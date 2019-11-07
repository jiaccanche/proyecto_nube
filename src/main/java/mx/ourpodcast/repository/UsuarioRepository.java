package mx.ourpodcast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    public Usuario findByToken(String token);
	public Usuario findByEmail(String email);
}