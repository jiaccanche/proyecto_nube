package mx.ourpodcast.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.MessageAlreadyExistsException;
import mx.ourpodcast.exceptions.MessageNotFoundException;
import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Message;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.MessageRepository;
import mx.ourpodcast.request.MessageRequest;

@Service
public class MessageService{

    @Autowired
    private MessageRepository messageRepository;
    
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	public Message getMessageById(Integer idMessage) {
        Optional<Message> optional = messageRepository.findById(idMessage);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new MessageNotFoundException("No existe un mensaje con el id " + idMessage);
        }
	}

	public List<Message> getMessagesByChat(Integer idChat) {
        ChatService chatService = new ChatService();
        Chat chat = chatService.getChatById(idChat);

        List<Message> messages = messageRepository.findAllByChat(chat);
        return messages;
	}

	public Message createMessage(@Valid MessageRequest request) {
		//Optional<Message> optional = messageRepository.findById(request.getIdMessage());
        //if(optional.isPresent()){
          //  throw new MessageAlreadyExistsException("Ya existe un mensje con el id " + request.getIdMessage());
        //}else{
            Message message = new Message();
            message.setContent(request.getContent());
            message.setSendDate(request.getSendDate());
            
            ChatService chatService = new ChatService();
            Chat chat = chatService.getChatById(request.getIdChat());
            message.setChat(chat);

            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());
            message.setUsuario(usuario);

            messageRepository.save(message);
            return message;
        //}
	}

	public Message updateMessage(@Valid MessageRequest request) {
		Optional<Message> optional = messageRepository.findById(request.getIdMessage());
        //if(optional.isPresent()){
            Message message = optional.get();
            message.setContent(request.getContent());
            
            ChatService chatService = new ChatService();
            Chat chat = chatService.getChatById(request.getIdChat());
            message.setChat(chat);

            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.getUsuarioById(request.getIdUsuario());
            message.setUsuario(usuario);

            messageRepository.save(message);
            return message;
        //}else{
          //  throw new MessageNotFoundException("No existe un mensaje con el id " + request.getIdMessage());
        //}
	}

	public void deleteMessage(Integer idMessage) {

        Optional<Message> optional = messageRepository.findById(idMessage);
        if(optional.isPresent()){
            messageRepository.delete(optional.get());
        }else{
            throw new MessageNotFoundException("No existe un mensaje con el id " + idMessage);
        }
	}

}