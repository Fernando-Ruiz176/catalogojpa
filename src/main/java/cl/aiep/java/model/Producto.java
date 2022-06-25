package cl.aiep.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Producto {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nombre;
	@Column(nullable = false, length = 100)
	private String marca;
	@Column(nullable = false, length = 100)
	private String descripcion;
	@ManyToOne
	private Categoria categoria;
	private String tipo;
	@Lob
	private byte[] datos;

}
