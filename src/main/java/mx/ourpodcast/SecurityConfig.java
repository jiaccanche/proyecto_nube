package mx.ourpodcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import mx.ourpodcast.filtros.TokenFiltro;
import mx.ourpodcast.repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenFiltro tokenFiltro = new TokenFiltro(usuarioRepository);
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable() 
            .httpBasic().disable()
            .authorizeRequests()
            .antMatchers("/login", "/registrar","/").permitAll()
            .anyRequest().authenticated()
            .and().logout().logoutUrl("/logout")
            //.anyRequest().permitAll()
            .and()
            .addFilterBefore(tokenFiltro, BasicAuthenticationFilter.class);
    }
}
