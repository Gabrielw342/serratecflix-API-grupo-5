package com.streamingflix_avaliacao.repository;
import com.streamingflix_avaliacao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username); // Query Method obrigatório
}