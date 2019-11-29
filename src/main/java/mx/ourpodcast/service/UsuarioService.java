package mx.ourpodcast.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.BadLoginException;
import mx.ourpodcast.exceptions.BadRequestException;
import mx.ourpodcast.exceptions.UsuarioAlreadyExistsException;
import mx.ourpodcast.exceptions.UsuarioBloqueado;
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.UsuarioRequest;
import mx.ourpodcast.request.loginRequest;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Integer LimiteIntentosLogin = new Integer(3);

	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(Integer idUsuario) {
       try{ Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new UsuarioNotFoundException("No existe un usuario con un indetificador " + idUsuario);
        }

        }catch(NullPointerException | NoSuchElementException e){
            throw new UsuarioNotFoundException(
                "No existe un usuario con identificador:" + idUsuario +", o no la petición es incorrecta.");
        }
	}

	public Usuario createUsuario(@Valid UsuarioRequest request) {
        try{     
            Optional<Usuario> optional = usuarioRepository.findByEmail(request.getEmail());
            if(optional.isPresent()){
            throw new UsuarioAlreadyExistsException(
                "Ya existe un usuario con ese email " +  request.getEmail()
                );
            }

        Usuario usuario = new Usuario();
        this.changeRequestUsuarioToUsuario(request,usuario);
        usuarioRepository.save(usuario);
        return usuario;
        }catch(NullPointerException | NoSuchElementException e){
        throw new BadRequestException("No se realizó la petición de manera correcta.");
        }
	}

	public Usuario updateUsuario(@Valid UsuarioRequest request) {
       try{
        Optional<Usuario> optional = usuarioRepository.findById(request.getIdUsuario());
        
        if(!optional.isPresent()){
        throw new UsuarioNotFoundException(
            "No existe un usuario con el identificador " + request.getIdUsuario()
            );
        }
        Usuario usuario = optional.get();
        this.changeRequestUsuarioToUsuario(request, usuario);
        return usuario;
        }catch(NullPointerException | NoSuchElementException e){
        throw new UsuarioNotFoundException(
            "El indetificador no puede ser null, o no la petición es incorrecta.");
        }
	}

	public void deleteUsuario() {
      try{
          Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          usuarioRepository.delete(user);
     }catch(NullPointerException | NoSuchElementException e){
        throw new UsuarioNotFoundException(
            "El identificador no puede ser null, o no la petición es incorrecta.");
        }
    }

    public Usuario login(loginRequest request) {
        Usuario usuario;

        try {
            usuario = usuarioRepository.findByUsername(request.getUsername()).get();
        }
        catch(NoSuchElementException e){
          throw new BadLoginException("El usuario/contraseña son incorrectos.") ;
        }

        if(!usuario.getPassword().equals(request.getPassword())){
            
            Integer intento = (usuario.getIntentoLogin() == null) ? 0 : usuario.getIntentoLogin();
            usuario.setIntentoLogin(intento + 1);
            usuarioRepository.save(usuario);
            boolean validar_intentos = usuario.getIntentoLogin().equals(LimiteIntentosLogin);
            if(validar_intentos) throw new UsuarioBloqueado();
            
            throw new BadLoginException("El usuario/contraseña son incorrectos.");
        }
    
        usuario.setToken(this.crearToken());
        LocalDateTime tiempoIniToken = LocalDateTime.now();
        usuario.setTiempoIniToken(tiempoIniToken);
        usuarioRepository.save(usuario);
        return usuario;
     
        
    }

    //Funciones para formato o seguridad
    public String crearToken(){
        return UUID.randomUUID().toString();
    }

    public LocalDate convertStringTLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);

        return localDate;
    }

    public void changeRequestUsuarioToUsuario(UsuarioRequest request, Usuario usuario){
            //Validar si es necesario crear un token
            if(usuario.getIdUsuario() == null){
                usuario.setState(true);
                usuario.setToken(this.crearToken());
                LocalDateTime tiempoIniToken = LocalDateTime.now();
                usuario.setTiempoIniToken(tiempoIniToken);
            }else{
                usuario.setState(request.getState());
            }
            
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            //Convertir string a fecha
            LocalDate birthday =  this.convertStringTLocalDate(request.getBirthday());
            usuario.setBirthday(birthday);
    }

    public void revokeToken (String token){
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setToken(null);
        user.setTiempoIniToken(null);
        usuarioRepository.save(user);
    }

}