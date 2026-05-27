package com.streamingflix.serraflixgrupo5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamingflix.serraflixgrupo5.entity.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
	
	public Optional<Foto> findByUsuarioId(Long usuarioId);
}
