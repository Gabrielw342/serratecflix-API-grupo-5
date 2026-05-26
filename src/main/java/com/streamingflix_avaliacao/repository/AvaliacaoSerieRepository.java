package com.streamingflix_avaliacao.repository;

import com.streamingflix_avaliacao.entity.AvaliacaoSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface AvaliacaoSerieRepository extends JpaRepository<AvaliacaoSerie, Long> {
   
    List<AvaliacaoSerie> findBySerieId(Long serieId);
}