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


    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest request){
        Comment comment = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("comments/streaming/{codeStreaming}")
    public ResponseEntity<List<Comment>> getCommentsByContent(@PathVariable String codeStreaming){
        List<Comment> comments = commentService.getAllCommentsByStreaming(codeStreaming);
        return ResponseEntity.ok().body(comments);
    }
}