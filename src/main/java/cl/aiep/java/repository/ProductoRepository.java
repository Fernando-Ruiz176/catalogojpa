package cl.aiep.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aiep.java.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
