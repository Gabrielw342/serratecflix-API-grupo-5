package com.streamingflix.serraflixgrupo5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamingflix.serraflixgrupo5.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

	List<Filme> findAllByOrderByNotaMediaDesc();
}