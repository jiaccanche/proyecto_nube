package mx.ourpodcast.exceptions;

public class MessageAlreadyExistsException extends RuntimeException{
    public MessageAlreadyExistsException(String message){
        super(message);
    }

}
