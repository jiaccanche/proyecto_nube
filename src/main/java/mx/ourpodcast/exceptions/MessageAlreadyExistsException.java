package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class MessageAlreadyExistsException extends RuntimeException{
    public MessageAlreadyExistsException(String message){
        super(message);
    }

}
