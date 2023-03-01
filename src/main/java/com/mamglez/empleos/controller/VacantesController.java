package com.mamglez.empleos.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.service.ICategoriasService;
import com.mamglez.empleos.service.IVacantesService;
import com.mamglez.empleos.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IVacantesService vacantesService;
	
	@Autowired
	private ICategoriasService categoriasService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = vacantesService.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "vacantes/listaVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		model.addAttribute("categorias", categoriasService.buscarTodas());
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multiPart) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.out.println("ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {
			// String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}

		
		vacantesService.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro ok");
		System.out.println("Nombre Vacante: " + vacante);
		return "redirect:/vacantes/index";
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
