package mx.ourpodcast.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(String message){
        super(message);
    }
}
