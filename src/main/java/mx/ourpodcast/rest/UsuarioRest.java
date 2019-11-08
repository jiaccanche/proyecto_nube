package mx.ourpodcast.rest;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.model.MessageInformation;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.request.UsuarioRequest;
import mx.ourpodcast.request.loginRequest;
import mx.ourpodcast.service.UsuarioService;

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

    @PostMapping("/registrar")
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

    @GetMapping("/exit")
    public ResponseEntity<MessageInformation> logout(ServletRequest request){
        HttpServletRequest servRequest = (HttpServletRequest) request;
        String token = servRequest.getHeader(HttpHeaders.AUTHORIZATION);
        usuarioService.revokeToken(token);
        MessageInformation msg = new MessageInformation();
        msg.setContent("Cierre de sesión");
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(loginRequest request){
        Usuario user = usuarioService.login(request);
        return ResponseEntity.ok(user);

    }


}