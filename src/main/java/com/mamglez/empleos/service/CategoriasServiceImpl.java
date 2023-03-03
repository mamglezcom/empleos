package com.mamglez.empleos.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mamglez.empleos.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

	private List<Categoria> categorias = new LinkedList<>();
	
	public CategoriasServiceImpl() {
		Categoria cat1 = new Categoria();
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		cat1.setDescripcion("Trabajos contabilidad");
		
		Categoria cat2 = new Categoria();
		cat2.setId(2);
		cat2.setNombre("Ventas");
		cat2.setDescripcion("Trabajos sistemas");
		
		Categoria cat3 = new Categoria();
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcion("Trabajos comunicaciones");
		
		Categoria cat4 = new Categoria();
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		cat4.setDescripcion("Trabajos arquitectura");
		
		Categoria cat5 = new Categoria();
		cat5.setId(5);
		cat5.setNombre("Educacion");
		cat5.setDescripcion("Trabajos educacion");
		
		Categoria cat6 = new Categoria();
		cat6.setId(6);
		cat6.setNombre("Desarrollo software");
		cat6.setDescripcion("Trabajos desarrollo");
		
		categorias.add(cat1);
		categorias.add(cat2);
		categorias.add(cat3);
		categorias.add(cat4);
		categorias.add(cat5);
		categorias.add(cat6);
	}
	
	@Override
	public void guardar(Categoria categoria) {
		categorias.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return categorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria cat : categorias) {
			if(cat.getId() == idCategoria) {
				return cat;
			}
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
