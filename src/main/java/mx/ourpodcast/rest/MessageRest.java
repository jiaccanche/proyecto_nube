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

import mx.ourpodcast.model.Message;
import mx.ourpodcast.request.MessageRequest;
import mx.ourpodcast.service.MessageService;

@RestController
public class MessageRest{

    @Autowired
    private MessageService messageService;

    @GetMapping("/message")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/message/{idMessage}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer idMessage){
        Message message = messageService.getMessageById(idMessage);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/message/chat/{idChat}")
    public ResponseEntity<List<Message>> getMessagesByChat(@PathVariable Integer idChat){
        List<Message> messages = messageService.getMessagesByChat(idChat);
        return ResponseEntity.ok().body(messages);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> createMesage(@Valid @RequestBody MessageRequest request){
        Message message = messageService.createMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/message")
    public ResponseEntity<Message> updateMessage(@Valid @RequestBody MessageRequest request){
        Message message = messageService.updateMessage(request);
        return ResponseEntity.ok().body(message);
    }

    @DeleteMapping("/message/{idMessage}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer idMessage){
        messageService.deleteMessage(idMessage);
        return ResponseEntity.ok().build();
    }
}