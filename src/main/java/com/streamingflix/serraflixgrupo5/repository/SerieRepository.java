package com.streamingflix.serraflixgrupo5.repository;

import java.util.Optional;
import java.util.List;

import com.streamingflix.serraflixgrupo5.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findById(Long id);

    List<Serie> findByCategoria_Id(Long categoria_id);

    @Query("SELECT s FROM Serie s JOIN s.categoria c WHERE c.nome = :nomeCategoria")
    List<Serie> findByCategoriaNome(String nomeCategoria);
}

