package mx.ourpodcast.filtros;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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

	public TokenFiltro(UsuarioRepository usuarioRepository) {
		this.usuarioRepo = usuarioRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

			System.out.println("Entro al filtro");
			HttpServletRequest servRequest = (HttpServletRequest) request;
			String token = servRequest.getHeader(HttpHeaders.AUTHORIZATION);

			Usuario user = usuarioRepo.findByToken(token);

			if (user != null ) {
			    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, null);
	        	SecurityContextHolder.getContext().setAuthentication(auth);
			}

			/*
			Usuario usuarioQueLLamo = 
            	((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			*/

			// Importante
			chain.doFilter(request, response);
	}   

	/*public boolean validateToken(Usuario user){
		int time_permitted = 1;
		Long diffTime =  this.restarDatetime(user.getTime_token(), new Date());
		System.out.println(diffTime);
		if(diffTime > (long) time_permitted || user.getToken() == null) return false;

		return true;

	}*/
	/*public Long restarDatetime(Date dateTimeToken, Date dateTimeRequest){

		long diffInMillies = dateTimeRequest.getTime() - dateTimeToken.getTime();
		return diffInMillies / (60 * 1000);
	  }*/
}