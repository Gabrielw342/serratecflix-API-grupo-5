package com.streamingflix.serraflixgrupo5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streamingflix.serraflixgrupo5.entity.AvaliacaoSerie;

@Repository
public interface AvaliacaoSerieRepository extends JpaRepository<AvaliacaoSerie, Long> {
     List<AvaliacaoSerie> findBySerieId(Long serieId);
}