package cl.aiep.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aiep.java.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
