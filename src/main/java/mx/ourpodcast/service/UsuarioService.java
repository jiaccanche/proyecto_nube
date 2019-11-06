package mx.ourpodcast.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.UsuarioAlreadyExistsException;
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.UsuarioRequest;

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
        Optional<Usuario> optional = usuarioRepository.findById(request.getIdUsuario());
        if(optional.isPresent()){
            throw new UsuarioAlreadyExistsException("Ya existe un usuario con el id " +  request.getIdUsuario());
        }else{
            Usuario usuario = new Usuario();
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            usuario.setBirthday(request.getBirthday());
            usuario.setState(true);
            usuario.setToken(null);
            usuarioRepository.save(usuario);
            return usuario;
        }
	}

	public Usuario updateUsuario(@Valid UsuarioRequest request) {
        Optional<Usuario> optional = usuarioRepository.findById(request.getIdUsuario());
        if(optional.isPresent()){
            Usuario usuario = optional.get();
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());
            usuario.setBirthday(request.getBirthday());
            usuario.setState(request.getState());
            usuario.setToken(request.getToken());
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

}