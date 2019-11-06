package mx.ourpodcast.exceptions;

public class CommentAlreadyExistException extends RuntimeException{
    public CommentAlreadyExistException(String message){
        super(message);
    }

}
