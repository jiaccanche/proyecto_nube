package mx.ourpodcast.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.service.UsuarioService;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.request.UsuarioRequest;

@RestController
public class UsuarioRest{

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer idUsuario){
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioRequest request){
        Usuario usuario = usuarioService.createUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/usuario")
    public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody UsuarioRequest request){
        Usuario usuario = usuarioService.updateUsuario(request);
        return ResponseEntity.ok().body(usuario);
    }

    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer idUsuario){
        usuarioService.deleteUsuarioById(idUsuario);
        return ResponseEntity.ok().build();
    }

}