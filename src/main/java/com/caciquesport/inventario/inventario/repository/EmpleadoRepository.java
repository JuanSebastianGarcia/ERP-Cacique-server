package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer>{

    Optional<Empleado> findByCedula(String cedula);
    
}
