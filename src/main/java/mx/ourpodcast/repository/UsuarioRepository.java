package mx.ourpodcast.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.ourpodcast.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    public Usuario findByToken(String token);
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByUsername(String username);
    public Optional<Usuario> finByidUsuario(int id);
}