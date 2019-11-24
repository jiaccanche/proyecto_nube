package mx.ourpodcast.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.ChatAlreadyExistsException;
import mx.ourpodcast.exceptions.ChatNotFoundException;
import mx.ourpodcast.exceptions.ChatNotValidException;
import mx.ourpodcast.exceptions.ChatUnauthorizedException;
import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.ChatRepository;
import mx.ourpodcast.request.ChatRequest;

@Service
public class ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioService service;

	public Chat getChatById(Integer idChat) {
        try{
            Optional<Chat> optional = chatRepository.findById(idChat);
            Chat chat = optional.get();
            Integer user1 = chat.getUsuario1().getIdUsuario();
            Integer user2 = chat.getUsuario2().getIdUsuario();

            if(userHasPermission(user1) || userHasPermission(user2) ){
                return optional.get();
            }else{
                throw new ChatUnauthorizedException("El usuario no tiene permiso para ver el chat de los usuarios " + user1 + " y " + user2);
            }

        }catch(NoSuchElementException | NullPointerException npe){
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public Chat createChat(@Valid ChatRequest request) {
        if(request.getIdChat() != null){
            throw new ChatAlreadyExistsException("Ya existe un chat con id " + request.getIdChat());

        }
        Usuario user1 = service.getUsuarioById(request.getIdUsuario1());
        Usuario user2 = service.getUsuarioById(request.getIdUsuario2());

        if(!userHasPermission(user1.getIdUsuario()) && !userHasPermission(user2.getIdUsuario())){
            throw new ChatUnauthorizedException("El usuario no tiene permiso para crear un chat entre los usuarios " + user1.getIdUsuario() + " y " + user2.getIdUsuario());
        }

        if(user1.getIdUsuario() == user2.getIdUsuario()){
            throw new ChatNotValidException("No puede crear un chat con el mismo usuario");
        }
        
        if(chatOfUsersExist(user1, user2)){
            throw new ChatAlreadyExistsException("Ya existe un chat para los usuarios dados");
        }

        Chat chat = new Chat();

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate initDate = LocalDate.parse(request.getInitDate(),DATEFORMATTER);
        chat.setInitDate(initDate);
        
        chat.setUsuario1(user1);

        chat.setUsuario2(user2);

        return chatRepository.save(chat);
        
    }
    
	public void deleteChat(Integer idChat) {
        try{
            Optional<Chat> optional = chatRepository.findById(idChat);
            Chat chat = optional.get();
            Integer user1 = chat.getUsuario1().getIdUsuario();
            Integer user2 = chat.getUsuario2().getIdUsuario();

            if(!userHasPermission(user1) && !userHasPermission(user2) ){
                throw new ChatUnauthorizedException("El usuario no tiene permiso para eliminar el chat de los usuarios " + user1 + " y " + user2);
            }
            chatRepository.delete(chat);
        }catch(NullPointerException |NoSuchElementException npe){
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public List<Chat> getAllChatsByUsuario(Integer idUsuario) {
       
        Usuario usuario = service.getUsuarioById(idUsuario);
        if(userHasPermission(idUsuario)){
            List<Chat> chats = chatRepository.findAllByUsuario1(usuario);
            chats.addAll(chatRepository.findAllByUsuario2(usuario));
            return chats;
        }else{
            throw new ChatUnauthorizedException("El usuario no tiene permiso para ver el chat del usuario " + idUsuario);
        }
    }

    private boolean userHasPermission(Integer idUsuario){
        Usuario user = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idUsuario == user.getIdUsuario();
    }


    private boolean chatOfUsersExist(Usuario user1, Usuario user2){
        
        try{
            Optional<Chat> chat = chatRepository.findByUsuario1AndUsuario2(user1, user2);
            Optional<Chat> chat2 = chatRepository.findByUsuario1AndUsuario2(user2, user1);
            return (chat.isPresent() || chat2.isPresent());
        }catch(NullPointerException | NoSuchElementException npe){
            return false;
        }
    }

}