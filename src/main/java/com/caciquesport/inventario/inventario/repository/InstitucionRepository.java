package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.Institucion;

@Repository
public interface InstitucionRepository extends JpaRepository<Institucion,Integer>{

    
}
