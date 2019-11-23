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

	public TokenFiltro(UsuarioRepository usuarioRepository) {
		this.usuarioRepo = usuarioRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		
		HttpServletRequest servRequest = (HttpServletRequest) request;
			
		String token = servRequest.getHeader(HttpHeaders.AUTHORIZATION);
		Usuario user = usuarioRepo.findByToken(token);

		boolean validate_credentials = this.validateUserParameters(user) && this.validateToken(user);
		if (validate_credentials) {
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, null);
	        SecurityContextHolder.getContext().setAuthentication(auth);
			}

		chain.doFilter(request, response);
	}   

	public boolean validateToken(Usuario user){
		int time_permitted = 500;
		Long diffTime =  this.restarDatetime(user.getTiempoIniToken(), LocalDateTime.now() );
		return !(diffTime > (long) time_permitted || user.getToken() == null);

	}
	public long restarDatetime(LocalDateTime dateTimeToken, LocalDateTime dateTimeRequest){
		Duration duration = Duration.between(dateTimeToken, dateTimeRequest);
		long seconds = duration.getSeconds();
		long minutes = seconds/SECONDS_ONE_MINUTE;
		return minutes;
	}

	public boolean validateUserParameters(Usuario user){
		boolean  result = false;
		try{
		result = (user != null 
			&& user.getIntentoLogin() < 3 
			&& user.getTiempoIniToken() != null);
			
		}catch(NullPointerException e){
			return result;
		}
			return result;
	}
}