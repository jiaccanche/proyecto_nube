package mx.ourpodcast.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.CommentNotFoundException;
import mx.ourpodcast.model.Comment;
import mx.ourpodcast.repository.CommentRepository;
import mx.ourpodcast.request.CommentRequest;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

	public List<Comment> getAllComments() {
		return null;
	}

	public List<Comment> getAllCommentsByStreaming(int idStreaming) {
		return null;
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
		return null;
	}

	public Comment updateComment(@Valid CommentRequest request) {
		return null;
	}

	public void deleteComment(int idComment) {
	}

}
