package com.mamglez.empleos.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mamglez.empleos.model.Usuario;
import com.mamglez.empleos.repository.IUsuariosRepository;
import com.mamglez.empleos.service.IUsuariosService;

@Service
public class UsuariosServiceJpa implements IUsuariosService {
	
	@Autowired
	private IUsuariosRepository usuariosRepo;

	@Override
	public void guardar(Usuario usuario) {
		usuariosRepo.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		usuariosRepo.deleteById(idUsuario);

	}

	@Override
	public List<Usuario> buscarTodos() {
		return usuariosRepo.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuariosRepo.findByUsername(username);
	}

}
