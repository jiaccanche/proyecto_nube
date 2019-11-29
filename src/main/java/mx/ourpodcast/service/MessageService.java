package mx.ourpodcast.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.ChatUnauthorizedException;
import mx.ourpodcast.exceptions.MessageNotFoundException;
import mx.ourpodcast.exceptions.MessageUnauthorizedException;
import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Message;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.MessageRepository;
import mx.ourpodcast.request.MessageRequest;

@Service
public class MessageService{

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private  ChatService chatService;

    public List<Message> getMessagebyContent(String content,Integer chat){
        Chat chat_obj = chatService.getChatById(chat);
        List<Message> mensajes = messageRepository.findByContentContainingAndChat(content,chat_obj);
        return mensajes;
    }


	public Message getMessageById(Integer idMessage) {
        try{
            Optional<Message> optional;
            optional = messageRepository.findById(idMessage);
            return optional.get();
            
        }catch(NullPointerException | NoSuchElementException e){
            throw new MessageNotFoundException("No existe el mensaje con id " + idMessage);
        }
	}

	public List<Message> getMessagesByChat(Integer idChat) {
        Chat chat = chatService.getChatById(idChat);

        List<Message> messages = messageRepository.findAllByChat(chat);
        
        return messages;
	}

	public Message createMessage(@Valid MessageRequest request) {

        Chat chat = chatService.getChatById(request.getIdChat());

        Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(usuario.getIdUsuario() != request.getIdUsuario()){
            throw new MessageUnauthorizedException("No puede enviar mensajes desde otro usuario");
        }    
        Message message = new Message();
        message.setContent(request.getContent());
        LocalDateTime sendDate = this.convertStringToLocalDateTime(request.getSendDate());
        message.setSendDate(sendDate);
        message.setUsuario(usuario);
        message.setChat(chat);
        
        return messageRepository.save(message);
	}

	public void deleteMessage(Integer idMessage) {
      try{  
            Optional<Message> optional = messageRepository.findById(idMessage);
            Integer id_user = optional.get().getUsuario().getIdUsuario();

            if(!validarPermiso(id_user)){
                throw new MessageUnauthorizedException("No tiene permiso de borrar el mensaje");
            }
            
            messageRepository.delete(optional.get());

        }catch(NullPointerException | NoSuchElementException e){
            throw new MessageNotFoundException("No existe el mensaje con id " + idMessage);
        }
    }

    //Funciones de formato o valdaciones
    public LocalDateTime convertStringToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);

        return localDateTime;
    }
    
    public boolean validarPermiso(Integer idusuario){
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idusuario == user.getIdUsuario();
    }

}