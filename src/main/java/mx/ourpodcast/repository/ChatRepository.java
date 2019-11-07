package mx.ourpodcast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>{

	List<Chat> getAllChatsByUsuario(Integer idUsuario);

}