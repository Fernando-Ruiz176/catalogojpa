package cl.aiep.java.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cl.aiep.java.model.Categoria;
import cl.aiep.java.model.Producto;
import cl.aiep.java.repository.CategoriaRepository;
import cl.aiep.java.repository.ProductoRepository;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
		@GetMapping ("/index")
		public String index() {
			return "index";
		}
		
		@GetMapping("/nuevo")
		private String nuevo(Producto producto, Model modelo) {
			List<Categoria> categorias = categoriaRepository.findAll();
			modelo.addAttribute("categorias",categorias);
			return"/producto/form";
		}
		
		
		@PostMapping("/procesar")
		public String procesarProducto(Producto producto,@RequestParam("archivo") MultipartFile archivo) {
			
			try {
				String nombreArchivo  	=  archivo.getOriginalFilename();
				String tipoArchivo		=  archivo.getContentType();
				byte[] contenidoArchivo =  archivo.getBytes();
				Producto p = Producto.builder()
										.datos(contenidoArchivo)
										.tipo(tipoArchivo)
										.nombre(producto.getNombre())
										.marca(producto.getMarca())
										.descripcion(producto.getDescripcion())
										.build()
				;
				productoRepository.saveAndFlush(p);
				return "redirect:/producto/listado";	
			} catch (Exception e) {
				return "/producto/form";
			}
		}
		
		@GetMapping("/listado")
		public String listado(Model modelo) {
			List<Producto> productos = productoRepository.findAll();
			modelo.addAttribute("productos", productos);
			return "producto/listado";
		}
		
		@GetMapping("/producto/img/{id}")
		public ResponseEntity<byte[]> mostrarImagenProducto(@PathVariable("id") Producto producto){
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline")
					.contentType( MediaType.valueOf(producto.getTipo()) )
					.body( producto.getDatos() )
					
			;
			
		}
		
		@GetMapping("/editar/{id}")
		public String editarProducto(@PathVariable(name = "id")Producto producto, Model modelo) {
			List<Categoria>categorias= categoriaRepository.findAll();
			modelo.addAttribute("categorias",categorias);
			modelo.addAttribute("producto", producto);	
			return "producto/form";
		}
		
		@GetMapping("/eliminar/{id}")
		public String eliminarProducto(@PathVariable Long id) {
			productoRepository.deleteById(id);
			return "redirect:/producto/listado";
		}

}
