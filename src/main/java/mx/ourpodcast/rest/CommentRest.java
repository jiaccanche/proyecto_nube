package mx.ourpodcast.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.service.CommentService;
import mx.ourpodcast.model.Comment;
import mx.ourpodcast.request.CommentRequest;

@RestController
public class CommentRest{

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getAllComments(){
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/comment/{idComment}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int idComment){
        Comment comment = commentService.getCommentById(idComment);
        return ResponseEntity.ok().body(comment);
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest request){
        Comment comment = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/comment")
    public ResponseEntity<Comment> updateComment(@Valid @RequestBody CommentRequest request){
        Comment comment = commentService.updateComment(request);
        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping("/comment/{idComment}")
    public ResponseEntity<Void> deleteComment(@PathVariable int idComment){
        commentService.deleteComment(idComment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("comment/streaming/{idStreaming}")
    public ResponseEntity<List<Comment>> getCommentsByContent(@PathVariable int idStreaming){
        List<Comment> comments = commentService.getAllCommentsByStreaming(idStreaming);
        return ResponseEntity.ok().body(comments);
    }
}