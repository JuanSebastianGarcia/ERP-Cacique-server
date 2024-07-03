package com.caciquesport.inventario.inventario.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caciquesport.inventario.inventario.model.entity.Cliente;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Cliente. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,String>{

}
