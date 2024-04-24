package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer>{

    
}
