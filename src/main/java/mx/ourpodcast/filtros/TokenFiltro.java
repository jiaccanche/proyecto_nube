package mx.ourpodcast.filtros;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.UsuarioRepository;

/**
 * TokenFiltro
 */
public class TokenFiltro extends GenericFilterBean {

	private final UsuarioRepository usuarioRepo;
	private final int SECONDS_ONE_MINUTE = 60;

	public TokenFiltro(final UsuarioRepository usuarioRepository) {
		this.usuarioRepo = usuarioRepository;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest servRequest = (HttpServletRequest) request;

		final String token = servRequest.getHeader(HttpHeaders.AUTHORIZATION);
		final Usuario user = usuarioRepo.findByToken(token);

		final boolean validate_credentials = this.validateUserParameters(user) && this.validateToken(user);
		if (validate_credentials) {
			final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, null);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	public boolean validateToken(final Usuario user) {
		final int time_permitted = 500;
		final Long diffTime = this.restarDatetime(user.getTiempoIniToken(), LocalDateTime.now());
		return !(diffTime > (long) time_permitted || user.getToken() == null);

	}

	public long restarDatetime(final LocalDateTime dateTimeToken, final LocalDateTime dateTimeRequest) {
		final Duration duration = Duration.between(dateTimeToken, dateTimeRequest);
		final long seconds = duration.getSeconds();
		final long minutes = seconds / SECONDS_ONE_MINUTE;
		return minutes;
	}

	public boolean validateUserParameters(final Usuario user){
	
		try{
			
			boolean  result = false;
			result = (user != null && user.getTiempoIniToken() != null);

			if(result){
				Integer intent = (user.getIntentoLogin() == null) ? 0:user.getIntentoLogin();
				user.setIntentoLogin(intent);
				usuarioRepo.save(user);
				result = result && user.getIntentoLogin() < 3;
			}
			
			return result;
			
		}catch(final NullPointerException e){
			return false;
		}
			
	}
}