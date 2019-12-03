package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MessageUnauthorizedException extends RuntimeException{
    public MessageUnauthorizedException(String message){
        super(message);
    }

}
