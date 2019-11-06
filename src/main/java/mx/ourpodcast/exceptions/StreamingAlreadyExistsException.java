package mx.ourpodcast.exceptions;

public class StreamingAlreadyExistsException extends RuntimeException{
    public StreamingAlreadyExistsException(String message){
        super(message);
    }
}
