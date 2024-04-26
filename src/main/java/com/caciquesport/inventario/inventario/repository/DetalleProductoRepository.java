package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.DetalleProducto;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad DetalleProducto. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto,Integer>{



}
