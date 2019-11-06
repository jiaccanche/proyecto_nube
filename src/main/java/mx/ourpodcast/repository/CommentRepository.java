package mx.ourpodcast.repository;

import mx.ourpodcast.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    
}