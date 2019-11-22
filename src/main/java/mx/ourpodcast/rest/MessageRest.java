package mx.ourpodcast.rest;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.ourpodcast.model.Message;
import mx.ourpodcast.model.MessageInformation;

import mx.ourpodcast.request.MessageRequest;
import mx.ourpodcast.service.MessageService;

@RestController
public class MessageRest {

    @Autowired
    private MessageService messageservice;

    @PostMapping("/message")
    /**
     * 
     * @param request
     * @return
     */
    public ResponseEntity<Message> crearMessage(@RequestBody MessageRequest request){
        Message msj = messageservice.createMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(msj);
    }

    @GetMapping("/message/{numchat}")
    /**
     * 
     * @param numchat
     * @return
     */
    public ResponseEntity<List<Message>> obtenerMessagebyChat(@PathVariable @NotBlank @NotNull @NumberFormat int numchat){
        List<Message> msjs = messageservice.getMessagesByChat(numchat);
        return ResponseEntity.status(HttpStatus.OK).body(msjs);   
    }

    @DeleteMapping("message/{message}")
    /**
     * 
     * @param message
     * @return
     */
    public ResponseEntity<MessageInformation> eliminarMensaje(@PathVariable @NotBlank @NotNull int message){
        messageservice.deleteMessage(message);
        String msj = "El mensaje ha sido eliminado";
        MessageInformation msj_r = new MessageInformation();
        msj_r.setContent(msj);
        return ResponseEntity.status(HttpStatus.OK).body(msj_r);
    }




}