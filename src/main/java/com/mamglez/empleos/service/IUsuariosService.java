package com.mamglez.empleos.service;

import java.util.List;

import com.mamglez.empleos.model.Usuario;

public interface IUsuariosService {
	void guardar(Usuario usuario);

	void eliminar(Integer idUsuario);
	
	List<Usuario> buscarTodos();
	
	Usuario buscarPorUsername(String username);
}

