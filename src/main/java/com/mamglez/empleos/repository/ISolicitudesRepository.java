package com.mamglez.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamglez.empleos.model.Solicitud;

public interface ISolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
