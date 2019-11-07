package mx.ourpodcast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Chat;
import mx.ourpodcast.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	List<Message> findAllByChat(Chat chat);

}