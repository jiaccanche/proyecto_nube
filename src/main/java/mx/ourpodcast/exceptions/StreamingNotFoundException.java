package mx.ourpodcast.exceptions;

public class StreamingNotFoundException extends RuntimeException{
    public StreamingNotFoundException(String message){
        super(message);
    }

}
