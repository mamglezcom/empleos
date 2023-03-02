package com.mamglez.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.mamglez.empleos.model.Categoria;

//public interface ICategoriasRepository extends CrudRepository<Categoria, Integer> {

public interface ICategoriasRepository extends JpaRepository<Categoria, Integer> {

}
