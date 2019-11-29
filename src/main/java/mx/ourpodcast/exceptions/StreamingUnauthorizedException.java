package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class StreamingUnauthorizedException extends RuntimeException{

    public StreamingUnauthorizedException(String message){
        super(message);
    }
}