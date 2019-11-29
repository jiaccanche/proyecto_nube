package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class StreamingAlreadyExistsException extends RuntimeException{
    public StreamingAlreadyExistsException(String message){
        super(message);
    }
}
