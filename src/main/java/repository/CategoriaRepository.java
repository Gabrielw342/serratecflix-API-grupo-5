package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}