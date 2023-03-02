package com.mamglez.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.mamglez.empleos.model.Vacante;
import com.mamglez.empleos.repository.IVacantesRepository;
import com.mamglez.empleos.service.IVacantesService;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {
	
	@Autowired
	private IVacantesRepository vacantesRepo;

	@Override
	public List<Vacante> buscarTodas() {
		return vacantesRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer id) {
		Optional<Vacante> optional = vacantesRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		vacantesRepo.save(vacante);

	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante) {
		vacantesRepo.deleteById(idVacante);
	}

}
