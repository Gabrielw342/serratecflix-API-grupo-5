package com.streamingflix_avaliacao.repository;

import com.streamingflix_avaliacao.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}