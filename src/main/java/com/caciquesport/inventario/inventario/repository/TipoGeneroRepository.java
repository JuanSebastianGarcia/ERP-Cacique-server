package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;

@Repository
public interface TipoGeneroRepository extends JpaRepository<TipoGenero,Integer> {

}
