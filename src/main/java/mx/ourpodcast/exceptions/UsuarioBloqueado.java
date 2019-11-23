package mx.ourpodcast.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class UsuarioBloqueado extends RuntimeException{
    public UsuarioBloqueado(){
        super("Tu cuenta ha sido bloqueado por favor comunicarte con el administrador");
    }
}
