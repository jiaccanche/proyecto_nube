package mx.ourpodcast.service;

import java.util.List;
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
        Optional<Comment> optional = commentRepository.findById(idComment);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new CommentNotFoundException("No existe un comentario con id "+ idComment);
        }
	}

	public Comment createComment(@Valid CommentRequest request) {
		Optional<Comment> optional = commentRepository.findById(request.getIdComment());
        if(optional.isPresent()){
            throw new CommentAlreadyExistException(request.getIdComment().toString());
        } else{
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
	}

	public Comment updateComment(@Valid CommentRequest request) {
		Optional<Comment> optional = commentRepository.findById(request.getIdComment());
        if(optional.isPresent()){
			Comment comment = optional.get();
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
        } else{
			throw new CommentNotFoundException("No existe un comentario con id "+ request.getIdComment());
        }
	}

	public void deleteComment(int idComment) {
		Optional<Comment> optional = commentRepository.findById(idComment);
        if(optional.isPresent()){
			Comment comment = optional.get();
			commentRepository.delete(comment);
		}else{
			throw new CommentNotFoundException("No existe un comentario con id "+ idComment);
		}

	}

}
