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
            throw new UsuarioNotFoundException("No existe un usuario con el id " + idUsuario);
        }
	}

	public Usuario createUsuario(@Valid UsuarioRequest request) {
        try{
            Optional<Usuario> optional = usuarioRepository.findByEmail(request.getEmail());
            if(optional.isPresent()){
                throw new UsuarioAlreadyExistsException("Ya existe un usuario con el id " +  request.getIdUsuario());
            }
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
            Usuario usuario = new Usuario();
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            //Convertir string a fecha
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(request.getBirthday(),DATEFORMATTER);
            usuario.setBirthday(birthday);
            usuario.setState(true);
            String token = UUID.randomUUID().toString();
            usuario.setToken(token);
            usuarioRepository.save(usuario);
            return usuario;
	}

	public Usuario updateUsuario(@Valid UsuarioRequest request) {
        Optional<Usuario> optional = usuarioRepository.findById(request.getIdUsuario());
        if(optional.isPresent()){
            Usuario usuario = optional.get();
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            //Convertir string a fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(request.getBirthday(),formatter);
            usuario.setBirthday(birthday);
            usuario.setState(request.getState());
            String token = UUID.randomUUID().toString();
            usuario.setToken(token);
            usuarioRepository.save(usuario);
            return usuario;
        }else{
            throw new UsuarioNotFoundException("No existe un usuario con el id " + request.getIdUsuario());
        }
	}

	public void deleteUsuarioById(Integer idUsuario) {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if(optional.isPresent()){
            usuarioRepository.delete(optional.get());
        }else{
            throw new UsuarioNotFoundException("No existe un usuario con el id " + idUsuario);
        }
    }
    
    public void revokeToken (String token){
        //Usuario user = usuarioRepository.findByToken(token);
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setToken(null);
        usuarioRepository.save(user);
    }

    public Usuario login(loginRequest request) {
        Usuario usuario;

        try {
            usuario = usuarioRepository.findByUsername(request.getUsername()).get();
            
  
        }
        catch(NoSuchElementException e){
            System.out.println("funciona");

          throw new BadRequestException();
        }

            
            if(!usuario.getPassword().equals(request.getPassword())){
                System.out.println("hola");
                throw new BadRequestException();
            }
    
            //Crear nuevo token
            usuario.setToken(this.crearToken());
            usuarioRepository.save(usuario);
            return usuario;
     
        
    }

    public String crearToken(){
        return UUID.randomUUID().toString();
    }

}