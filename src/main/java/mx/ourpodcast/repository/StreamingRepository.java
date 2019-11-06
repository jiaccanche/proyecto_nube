package mx.ourpodcast.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Integer>{

	List<Streaming> findAllByDate(LocalDateTime dateTime);

	List<Streaming> findAllByUsuario(Usuario usuario);

}