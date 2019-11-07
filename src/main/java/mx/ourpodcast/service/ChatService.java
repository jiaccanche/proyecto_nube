package mx.ourpodcast.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ourpodcast.exceptions.ChatAlreadyExistsException;
import mx.ourpodcast.exceptions.ChatNotFoundException;
import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Usuario;
import mx.ourpodcast.repository.ChatRepository;
import mx.ourpodcast.request.ChatRequest;

@Service
public class ChatService{

    @Autowired
    private ChatRepository chatRepository;

	public List<Chat> getAllChats() {
		return chatRepository.findAll();
	}

	public Chat getChatById(Integer idChat) {
        Optional<Chat> optional = chatRepository.findById(idChat);
        if (optional.isPresent()) {
            return optional.get();
        }else{
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public Chat createChat(@Valid ChatRequest request) {
        Optional<Chat> optional = chatRepository.findById(request.getIdChat());
		if (optional.isPresent()) {
            throw new ChatAlreadyExistsException("Ya existe un chat con id " + request.getIdChat());
        }else{
            Chat chat = new Chat();
            chat.setInitDate(request.getInitDate());

            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario1 = usuarioService.getUsuarioById(request.getIdUsuario1());
            chat.setUsuario1(usuario1);

            Usuario usuario2 = usuarioService.getUsuarioById(request.getIdUsuario2());
            chat.setUsuario2(usuario2);

            chatRepository.save(chat);
            return chat;
        }
	}

	public Chat updateChat(@Valid ChatRequest request) {
		Optional<Chat> optional = chatRepository.findById(request.getIdChat());
		if (optional.isPresent()) {
            Chat chat = optional.get();
            chat.setInitDate(request.getInitDate());

            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario1 = usuarioService.getUsuarioById(request.getIdUsuario1());
            chat.setUsuario1(usuario1);

            Usuario usuario2 = usuarioService.getUsuarioById(request.getIdUsuario2());
            chat.setUsuario2(usuario2);

            chatRepository.save(chat);
            return chat;
        }else{
            throw new ChatNotFoundException("No existe un chat con id " + request.getIdChat());
        }
	}

	public void deleteChat(Integer idChat) {
		Optional<Chat> optional = chatRepository.findById(idChat);
        if (optional.isPresent()) {
            chatRepository.delete(optional.get());
        }else{
            throw new ChatNotFoundException("No existe un chat con id " + idChat);
        }
	}

	public List<Chat> getAllChatsByUsuario(Integer idUsuario) {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);

        List<Chat> chats = chatRepository.findAllByUsuario1(usuario);
        chats.addAll(chatRepository.findAllByUsuario2(usuario));
        return chats;
	}

}