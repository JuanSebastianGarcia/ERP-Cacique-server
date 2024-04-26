package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.entity.Producto;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Producto. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{

   

}
