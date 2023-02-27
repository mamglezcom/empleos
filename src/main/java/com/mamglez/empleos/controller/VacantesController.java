package com.mamglez.empleos.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.service.IVacantesService;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Autowired
	private IVacantesService vacantesService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		//TODO 1. Obtener todas las vacantes (recuperarlas con la clase de servicio).
		List<Vacante> vacantes = vacantesService.buscarTodas();
		//TODO 2. Agregar al modelo el listado de vacantes.
		model.addAttribute("vacantes", vacantes);
		//TODO 3. Renderizar las vacantes en la vista (integrar el archivo listaVacantes.html).
		//TODO 4. Agregar al menu una opcion llamada "Vacantes" configurando la URL "vacantes/index".
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear() {
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante) {
		vacantesService.guardar(vacante);
		System.out.println("Nombre Vacante: " + vacante);
		return "vacantes/listVacantes";
	}
	
//	@PostMapping("/save")
//	public String guardar(@RequestParam("nombre") String nombre, 
//			@RequestParam("descripcion") String descripcion,
//			@RequestParam("categoria") String categoria,
//			@RequestParam("estatus") String estatus,
//			@RequestParam("fecha") String fecha,
//			@RequestParam("destacado") int destacado,
//			@RequestParam("salario") double salario,
//			//@RequestParam("archivoImagen") String archivoImagen,
//			@RequestParam("detalles") String detalles) {
//		
//		System.out.println("Nombre Vacante: " + nombre);
//		System.out.println("Descripcion Vacante: " + descripcion);
//		System.out.println("Estatus Vacante: " + estatus);
//		System.out.println("Fecha pub Vacante: " + fecha);
//		System.out.println("Destacado: " + destacado);
//		System.out.println("Salario Vacante: " + salario);
//		System.out.println("Detalle Vacante: " + detalles);
//		return "vacantes/listVacantes";
//	}
	
	@GetMapping("/delete")
	public String eliminar(@RequestParam("id") int idVacante, Model model) {
		System.out.println("borrando vacante id: " + idVacante);
		model.addAttribute("id",idVacante);
		return "mensaje";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalles(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = vacantesService.buscarPorId(idVacante);
		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante",vacante);
		return "detalle";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}
	
}
