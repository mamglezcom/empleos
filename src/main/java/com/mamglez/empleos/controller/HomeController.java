package com.mamglez.empleos.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private IVacantesService vacantesService;
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = vacantesService.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero comunicaciones");
		vacante.setDescripcion("se solicita ingeniero para soporte");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<>();
		lista.add("ingeniero sistemas");
		lista.add("Auxiliar sistemas");
		lista.add("vendedor sistemas");
		lista.add("arquitecto sistemas");
	
		model.addAttribute("empleos", lista);
		return "listado";
	}

	@GetMapping("/")
	public String mostrarHome(Model model) {
//		List<Vacante> lista = vacantesService.buscarTodas();
//		model.addAttribute("vacantes", lista);
		return "home";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("vacantes", vacantesService.buscarDestacadas());
	}
	
}
