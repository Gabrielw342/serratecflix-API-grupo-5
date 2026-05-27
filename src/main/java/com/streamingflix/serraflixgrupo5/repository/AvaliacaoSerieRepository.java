package com.streamingflix.serraflixgrupo5.repository;

import com.streamingflix.serraflixgrupo5.entity.AvaliacaoSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoSerieRepository extends JpaRepository<AvaliacaoSerie, Long> {
}