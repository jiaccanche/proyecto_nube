package mx.ourpodcast.exceptions;

public class PodcastAlreadyExistException extends RuntimeException{

    public PodcastAlreadyExistException(String message){
        super(message);
    }
}
