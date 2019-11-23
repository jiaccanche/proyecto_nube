package mx.ourpodcast.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioAlreadyExistsException extends RuntimeException{
    public UsuarioAlreadyExistsException(String message){
        super(message);
    }
}
