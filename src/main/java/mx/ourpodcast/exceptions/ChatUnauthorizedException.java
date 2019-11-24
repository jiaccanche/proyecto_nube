package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ChatUnauthorizedException extends RuntimeException{
    public ChatUnauthorizedException(String message){
        super(message);
    }
}
