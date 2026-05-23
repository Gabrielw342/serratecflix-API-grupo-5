package com.streamingflix.serraflixgrupo5.repository;

import com.streamingflix.serraflixgrupo5.entity.ListaFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaFavoritosRepository extends JpaRepository<ListaFavoritos, Long> {
}