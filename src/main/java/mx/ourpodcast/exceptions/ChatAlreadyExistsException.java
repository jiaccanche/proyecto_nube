package mx.ourpodcast.exceptions;

public class ChatAlreadyExistsException extends RuntimeException{
    public ChatAlreadyExistsException(String message){
        super(message);
    }
}
