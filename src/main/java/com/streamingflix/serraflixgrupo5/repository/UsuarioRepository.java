package com.streamingflix.serraflixgrupo5.repository;

import com.streamingflix.serraflixgrupo5.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}