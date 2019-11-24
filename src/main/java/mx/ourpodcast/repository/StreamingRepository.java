package mx.ourpodcast.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Streaming;
import mx.ourpodcast.model.Usuario;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Integer>{

	//TODO: Añadir query para obtener los streamings que se pongan en una fecha determinada

	List<Streaming> findByUsuario(Usuario usuario);
	Streaming findByCode(String code);
    
	List<Streaming> findAllBybeginDateTime(LocalDateTime dateTime);
	List<Streaming> findAllByfinishDateTime(LocalDateTime dateTime);

}