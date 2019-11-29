package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.LOCKED)
public class StreamingNotAviableException extends RuntimeException{
    public StreamingNotAviableException(String message){
        super(message);
    }
}