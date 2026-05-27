package com.streamingflix.serraflixgrupo5.repository;

import com.streamingflix.serraflixgrupo5.entity.AvaliacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, Long> {
}