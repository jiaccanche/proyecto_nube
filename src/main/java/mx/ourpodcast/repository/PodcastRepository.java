package mx.ourpodcast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.model.Usuario;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Integer>{

	public List<Podcast> findAllByUsuario(Usuario usuario);


}