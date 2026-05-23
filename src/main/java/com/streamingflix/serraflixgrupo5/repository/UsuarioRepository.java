package com.streamingflix.serraflixgrupo5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamingflix.serraflixgrupo5.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsername(String username);
	Optional<Usuario> findByEmail(String email);
	
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
