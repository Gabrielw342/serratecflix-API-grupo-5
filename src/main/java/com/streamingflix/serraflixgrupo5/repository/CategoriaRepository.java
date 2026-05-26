package com.streamingflix_avaliacao.repository;
import com.streamingflix_avaliacao.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}