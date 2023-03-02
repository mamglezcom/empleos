package com.mamglez.empleos.service;

import java.util.List;

import com.mamglez.empleos.model.Vacante;

public interface IVacantesService {
	
	List<Vacante> buscarTodas();
	
	Vacante buscarPorId(Integer id);

	void guardar(Vacante vacante);
	
	List<Vacante> buscarDestacadas();
	
	void eliminar(Integer idVacante);
}
