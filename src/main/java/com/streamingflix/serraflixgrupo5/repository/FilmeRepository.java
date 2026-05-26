package com.streamingflix_avaliacao.repository;
import com.streamingflix_avaliacao.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("SELECT f FROM Filme f JOIN f.categorias c WHERE c.nome = :nomeCategoria")
    List<Filme> buscarPorCategoria(@Param("nomeCategoria") String nomeCategoria);
}