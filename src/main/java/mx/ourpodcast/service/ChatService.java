package mx.ourpodcast.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.ChatAlreadyExistsException;
import mx.ourpodcast.exceptions.ChatNotFoundException;
import mx.ourpodcast.exceptions.UsuarioNotFoundException;
import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Message;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.ChatRepository;
import mx.ourpodcast.repository.UsuarioRepository;
import mx.ourpodcast.request.ChatRequest;

@Service
public class ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioService service;

	public Chat getChatById(Integer idChat) {
        Optional<Chat> optional = chatRepository.findById(idChat);
        if (optional.isPresent()) {
            return optional.get();
        }else{
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public Chat createChat(@Valid ChatRequest request) {
        if(request.getIdChat() != null){
            throw new ChatAlreadyExistsException("Ya existe un chat con id " + request.getIdChat());

        }else{
            Usuario user1 = service.getUsuarioById(request.getIdUsuario1());
            Usuario user2 = service.getUsuarioById(request.getIdUsuario2());
            if(chatExist(user1, user2)){
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
    }
    
	public void deleteChat(Integer idChat) {
        try{
		    Optional<Chat> optional = chatRepository.findById(idChat);
            chatRepository.delete(optional.get());
        }catch(NullPointerException |NoSuchElementException npe){
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public List<Chat> getAllChatsByUsuario(Integer idUsuario) {
       
        Usuario usuario = service.getUsuarioById(idUsuario);

        List<Chat> chats = chatRepository.findAllByUsuario1(usuario);
        chats.addAll(chatRepository.findAllByUsuario2(usuario));
        return chats;
    }


    private boolean chatExist(Usuario user1, Usuario user2){
        
        try{
            Optional<Chat> chat = chatRepository.findByUsuario1AndUsuario2(user1, user2);
            return true;
        }catch(NullPointerException npe){
            return false;
        }
    }

}