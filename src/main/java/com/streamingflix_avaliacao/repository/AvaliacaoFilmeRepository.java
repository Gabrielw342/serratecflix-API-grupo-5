package com.streamingflix_avaliacao.repository;

import com.streamingflix_avaliacao.entity.AvaliacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, Long> {
    
    List<AvaliacaoFilme> findByFilmeId(Long filmeId);
}