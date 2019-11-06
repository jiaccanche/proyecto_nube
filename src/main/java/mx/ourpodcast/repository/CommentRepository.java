package mx.ourpodcast.repository;

import mx.ourpodcast.model.Comment;
import mx.ourpodcast.model.Streaming;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findAllByStreaming(Streaming streaming);
    
}