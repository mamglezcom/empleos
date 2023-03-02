package com.mamglez.empleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mamglez.empleos.model.Categoria;
import com.mamglez.empleos.model.Vacante;
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
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Integer idCategoria, RedirectAttributes attributes) {
		System.out.println("borrando vacante id: " + idCategoria);
		try {
			categoriasService.eliminar(idCategoria);
			attributes.addFlashAttribute("msg", "Borrado ok");
		} catch (Exception e) {
			attributes.addFlashAttribute("msgImpossible","no se puede eliminar la categoria " + idCategoria + ", hay vacantes que la tienen asociada");
			return "redirect:/categorias/index";
			//e.printStackTrace();
		}
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Integer idVacante, Model model) {
		Categoria categoria = categoriasService.buscarPorId(idVacante);
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}
	

	// @GetMapping("/index")
//	@RequestMapping(value="/index", method=RequestMethod.GET)
//	public String mostrarIndex(Model model) {
//	return "categorias/listCategorias";
//	}
	
//	// @PostMapping("/save")
//	@RequestMapping(value="/save", method=RequestMethod.POST)
//	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion) {
//		System.out.println("categoria: " + nombre);
//		System.out.println("descripcion: " + descripcion);
//		return "categorias/listCategorias";
//	}

	
}
