package mx.ourpodcast.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ChatAlreadyExistsException extends RuntimeException{
    public ChatAlreadyExistsException(String message){
        super(message);
    }
}
