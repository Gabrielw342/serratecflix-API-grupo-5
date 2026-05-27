package com.streamingflix.serraflixgrupo5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamingflix.serraflixgrupo5.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}