package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.entity.Empleado;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Empleado. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer>{

    /*
     * buscar un empleado por la cedula
     * 
     * @param cedula del empleado 
     */
    Optional<Empleado> findByCedula(String cedula);

    /*
     * buscar un empleado por el email
     * 
     * @param email del empleado
     */
    Optional<Empleado> findByEmail(String email);
    
}
