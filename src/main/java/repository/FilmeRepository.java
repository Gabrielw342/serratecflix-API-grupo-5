package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}