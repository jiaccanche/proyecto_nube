package mx.ourpodcast.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private StreamingService streamingService;


	public List<Comment> getAllCommentsByStreaming(String codeStreaming) {

		Streaming streaming = streamingService.getStreamingByCode(codeStreaming);
		List<Comment> comments = commentRepository.findAllByStreaming(streaming);
		return comments;
	}

	public Comment createComment(@Valid CommentRequest request) {
		
			Comment comment = new Comment();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	LocalDateTime localDateTime = LocalDateTime.parse(request.getCreationDate(),formatter);
			comment.setCreationDate(localDateTime);
			comment.setContent(request.getContent());
			
			//UsuarioService usuarioService = new UsuarioService();
			Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());
			
			//StreamingService streamingService = new StreamingService();
			Streaming streaming = streamingService.getStreamingByCode(request.getCodeStreaming());
			comment.setStreaming(streaming);

			comment.setUsuario(usuario);
			
            return commentRepository.save(comment);
        
	}

}
