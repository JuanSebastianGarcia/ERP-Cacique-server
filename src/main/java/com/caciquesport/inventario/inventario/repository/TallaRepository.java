package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.Talla;

@Repository
public interface TallaRepository extends JpaRepository<Talla,Integer>{

}
