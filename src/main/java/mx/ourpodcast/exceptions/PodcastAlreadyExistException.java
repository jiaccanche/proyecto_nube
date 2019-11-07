package mx.ourpodcast.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class PodcastAlreadyExistException extends RuntimeException{

    public PodcastAlreadyExistException(String message){
        super(message);
    }
}
