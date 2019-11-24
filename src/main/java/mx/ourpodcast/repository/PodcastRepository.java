package mx.ourpodcast.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Podcast;
import mx.ourpodcast.model.Usuario;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Integer>{

	Optional<Podcast> findByCode(String code);
	public List<Podcast> findByUsuario(Usuario usuario);

}