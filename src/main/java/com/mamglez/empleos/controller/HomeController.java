package com.mamglez.empleos.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mamglez.empleos.model.Perfil;
import com.mamglez.empleos.model.Usuario;
import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.service.ICategoriasService;
import com.mamglez.empleos.service.IUsuariosService;
import com.mamglez.empleos.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ICategoriasService categoriasService;
	
	@Autowired
	private IVacantesService vacantesService;
	
	@Autowired
	private IUsuariosService usuariosService;
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		System.out.println("registrando usuario...");
		usuario.setFechaRegistro(new Date());
		usuario.setEstatus(1);
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		usuariosService.guardar(usuario);
		attributes.addFlashAttribute("msg", "usuario insertado");
		
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("nombre usuario: " + username);
		for(GrantedAuthority rol: auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = usuariosService.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("usuario: " + usuario);
			session.setAttribute("usuario", usuario);
		}
		return "redirect:/";
	}
	
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
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("buscando por " + vacante);
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		Example<Vacante> example = Example.of(vacante,matcher);
		List<Vacante> lista = vacantesService.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptato con Bcrypt: " + passwordEncoder.encode(texto);
	}
	
	@GetMapping("/login" )
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler =
		new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	//emptyAsNull true if an empty String is to be transformed into null
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("search", vacanteSearch);
		model.addAttribute("vacantes", vacantesService.buscarDestacadas());
		model.addAttribute("categorias", categoriasService.buscarTodas());
	}
	
}
