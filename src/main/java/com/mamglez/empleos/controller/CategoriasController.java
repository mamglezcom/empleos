package com.mamglez.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mamglez.empleos.model.Categoria;
import com.mamglez.empleos.service.ICategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	@Qualifier("categoriasServiceJpa")
	private ICategoriasService categoriasService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> categorias = categoriasService.buscarTodas();
		model.addAttribute("categorias", categorias);
		return "categorias/listCategorias";
	}

	// @GetMapping("/index")
//	@RequestMapping(value="/index", method=RequestMethod.GET)
//	public String mostrarIndex(Model model) {
//	return "categorias/listCategorias";
//	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
	return "categorias/formCategoria";
	}
	
	@PostMapping("/save")
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			for(ObjectError err : result.getAllErrors()) {
				System.out.println("ocurrio un error: " + err.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		categoriasService.guardar(categoria);
		attributes.addFlashAttribute("msg", "Registro ok");
		return "redirect:/categorias/index";
	}
	
//	// @PostMapping("/save")
//	@RequestMapping(value="/save", method=RequestMethod.POST)
//	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion) {
//		System.out.println("categoria: " + nombre);
//		System.out.println("descripcion: " + descripcion);
//		return "categorias/listCategorias";
//	}

	
}
