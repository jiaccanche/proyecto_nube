package mx.ourpodcast.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.CommentAlreadyExistException;
import mx.ourpodcast.exceptions.CommentNotFoundException;
import mx.ourpodcast.model.Comment;
import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.CommentRepository;
import mx.ourpodcast.request.CommentRequest;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}

	public List<Comment> getAllCommentsByStreaming(int idStreaming) {

		StreamingService streamingService = new StreamingService();
		Streaming streaming = streamingService.getStreamingById(idStreaming);
		List<Comment> comments = commentRepository.findAllByStreaming(streaming);
		return comments;
	}

	public Comment getCommentById(int idComment) {
	Comment commentEncontrado;	
    
    try{
      commentEncontrado = commentRepository.findById(idComment).get();
    }
    catch(NoSuchElementException e){
		throw new CommentNotFoundException("No existe un comentario con id "+ idComment);
      
    }
     return commentEncontrado;
	}

	public Comment createComment(@Valid CommentRequest request) {
		
			Comment comment = new Comment();
			comment.setCreationDate(request.getCreationDate());
			comment.setContent(request.getContent());
			
			StreamingService streamingService = new StreamingService();
			Streaming streaming = streamingService.getStreamingById(request.getIdStreaming());
			comment.setStreaming(streaming);
			
			UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());

			comment.setUsuario(usuario);
			commentRepository.save(comment);
            return comment;
        
	}

	public Comment updateComment(@Valid CommentRequest request) {
		Comment commentEncontrado;	
    
    try{
      commentEncontrado = commentRepository.findById(request.getIdComment()).get();
    }
    catch(NoSuchElementException e){
		throw new CommentNotFoundException("No existe un comentario con esos datos " + request);
      
    }
    
			commentEncontrado.setCreationDate(request.getCreationDate());
			commentEncontrado.setContent(request.getContent());
			
			StreamingService streamingService = new StreamingService();
			Streaming streaming = streamingService.getStreamingById(request.getIdStreaming());
			commentEncontrado.setStreaming(streaming);
			
			UsuarioService usuarioService = new UsuarioService();
			Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());
			
			commentEncontrado.setUsuario(usuario);
			commentRepository.save(commentEncontrado);
            return commentEncontrado;
        
	}

	public void deleteComment(int idComment) {
		Comment commentEncontrado;	
    
    try{
      commentEncontrado = commentRepository.findById(idComment).get();
    }
    catch(NoSuchElementException e){
		throw new CommentNotFoundException("No existe un comentario con id "+ idComment);
      
    }
     
			commentRepository.delete(commentEncontrado);
		
			throw new CommentNotFoundException("No existe un comentario con id "+ idComment);
		

	}

}
