package mx.ourpodcast.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Integer>{

	Optional<Podcast> findByCode(String code);

}