package mx.ourpodcast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Usuario;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>{


	List<Chat> findAllByUsuario1(Usuario usuario);
	List<Chat> findAllByUsuario2(Usuario usuario);
}