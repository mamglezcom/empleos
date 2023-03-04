package com.mamglez.empleos.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mamglez.empleos.model.Solicitud;
import com.mamglez.empleos.model.Usuario;
import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.service.ISolicitudesService;
import com.mamglez.empleos.service.IUsuariosService;
import com.mamglez.empleos.service.IVacantesService;
import com.mamglez.empleos.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	@Value("${empleosapp.ruta.cv}")
	private String rutaCv;
	
	@Autowired
	private IVacantesService vacantesService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@Autowired
	private ISolicitudesService solicitudesService;
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes) {
		solicitudesService.eliminar(idSolicitud);
		attributes.addFlashAttribute("msg", "solicitud eliminada");
		return "redirect:/solicitudes/indexPaginate";
		
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Solicitud> lista = solicitudesService.buscarTodas(page);
		model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
	}
	
	@GetMapping("/create/{idVacante}")
	public String crearSolicitud(Solicitud solicitud, @PathVariable("idVacante") Integer idVacante, Model model) {
		Vacante vacante = vacantesService.buscarPorId(idVacante);
		System.out.println("idVacante: " + idVacante);
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, 
			@RequestParam("archivoCV") MultipartFile multipart, 
			Authentication authentication,
			RedirectAttributes attributes) {
		
		String username = authentication.getName();
		
		if(result.hasErrors()) {
			System.out.println("Existen errores");
			return "solicitudes/formSolicitud";
		}
		if(!multipart.isEmpty()) {
			String nombreArchivo = Utileria.guardarArchivo(multipart, rutaCv);
			if(nombreArchivo != null) {
				solicitud.setArchivo(nombreArchivo);
			}
		}
		
		Usuario usuario = usuariosService.buscarPorUsername(username);
		solicitud.setUsuario(usuario);
		solicitudesService.guardar(solicitud);
		attributes.addFlashAttribute("msg","Cv enviado");
		
		System.out.println("Solicitud: " + solicitud);
		return "redirect:/";
	}

}
