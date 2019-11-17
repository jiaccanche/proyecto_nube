package mx.ourpodcast.service;

import java.time.LocalDate;
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
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.UsuarioRequest;
import mx.ourpodcast.request.loginRequest;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(Integer idUsuario) {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new UsuarioNotFoundException("No existe un usuario con un indetificador " + idUsuario);
        }
	}

	public Usuario createUsuario(@Valid UsuarioRequest request) {
           
        Optional<Usuario> optional = usuarioRepository.findByEmail(request.getEmail());
        if(optional.isPresent()){
            throw new UsuarioAlreadyExistsException("Ya existe un usuario con ese email " +  request.getEmail());
        }

        Usuario usuario = new Usuario();
        this.changeRequestUsuarioTUsuario(request,usuario);
        usuarioRepository.save(usuario);
        return usuario;
	}

	public Usuario updateUsuario(@Valid UsuarioRequest request) {
        if(request.getIdUsuario() == null) throw new BadRequestException("El IdUsuario no puede ser null");
        
        Optional<Usuario> optional = usuarioRepository.findById(request.getIdUsuario());
        
        if(!optional.isPresent()){
        throw new UsuarioNotFoundException(
            "No existe un usuario con el identificador " + request.getIdUsuario()
            );
        }
        Usuario usuario = optional.get();
        this.changeRequestUsuarioTUsuario(request, usuario);
        return usuario;
        
	}

	public void deleteUsuarioById(Integer idUsuario) {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if(optional.isPresent()){
            usuarioRepository.delete(optional.get());
        }else{
            throw new UsuarioNotFoundException("No existe un usuario con el identificado " + idUsuario);
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
            throw new BadLoginException("El usuario/contraseña son incorrectos.");
        }
    
        usuario.setToken(this.crearToken());
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

    public void changeRequestUsuarioTUsuario(UsuarioRequest request, Usuario usuario){
            //Validar si es necesario crear un token
            if(usuario.getIdUsuario() == null){
                usuario.setState(true);
                usuario.setToken(this.crearToken());
            }else{
                usuario.setState(request.getState());
            }
            
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            //Convertir string a fecha
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(request.getBirthday(),DATEFORMATTER);
            usuario.setBirthday(birthday);
    }

    public void revokeToken (String token){
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setToken(null);
        usuarioRepository.save(user);
    }

}