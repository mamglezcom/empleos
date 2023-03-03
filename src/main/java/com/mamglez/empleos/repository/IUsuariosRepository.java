package com.mamglez.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mamglez.empleos.model.Usuario;


public interface IUsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);

}
