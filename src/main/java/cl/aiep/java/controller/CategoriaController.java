package cl.aiep.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.aiep.java.model.Categoria;
import cl.aiep.java.repository.CategoriaRepository;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping ("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/nuevo")
	private String nuevo(Categoria categoria) {
		return"/categoria/form";
	}
	
	@PostMapping("/procesar")
	public String procesar(@Valid Categoria categoria, BindingResult informeValidacion) {
		if(informeValidacion.hasErrors()) return "categoria/form";
		categoriaRepository.saveAndFlush(categoria);
		return "redirect:/categoria/listado";
	}
	
	@GetMapping("/listado")
	public String listado(Model modelo) {
		List<Categoria> categorias = categoriaRepository.findAll();
		modelo.addAttribute("categorias", categorias);
		return "categoria/listado";
	}
	
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable(name = "id")Categoria categoria, Model modelo) {
		modelo.addAttribute("categoria", categoria);
		return "categoria/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCategoria(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
		return "redirect:/categoria/listado";
	}

}
