package com.streamingflix_avaliacao.repository;

import com.streamingflix_avaliacao.entity.ListaFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaFavoritosRepository extends JpaRepository<ListaFavoritos, Long> {
}