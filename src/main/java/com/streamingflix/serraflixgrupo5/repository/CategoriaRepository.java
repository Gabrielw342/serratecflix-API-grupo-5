package com.streamingflix.serraflixgrupo5.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.streamingflix.serraflixgrupo5.entity.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}