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

import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Message;
import mx.ourpodcast.request.ChatRequest;
import mx.ourpodcast.service.ChatService;

@RestController
public class ChatRest{

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat/{idChat}")
    public ResponseEntity<Chat> getChatById(@PathVariable Integer idChat){
        Chat chat = chatService.getChatById(idChat);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/chat")
    public ResponseEntity<Chat> createChat(@Valid @RequestBody ChatRequest request){
        Chat chat = chatService.createChat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }

    @DeleteMapping("/chat/{idChat}")
    public ResponseEntity<Void> deleteChat(@PathVariable Integer idChat){
        chatService.deleteChat(idChat);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat/usuario/{idUsuario}")
    public ResponseEntity<List<Chat>> getAllChatsByUsuario(@PathVariable Integer idUsuario){
        List<Chat> chats = chatService.getAllChatsByUsuario(idUsuario);
        return ResponseEntity.ok(chats);
    }

   
}