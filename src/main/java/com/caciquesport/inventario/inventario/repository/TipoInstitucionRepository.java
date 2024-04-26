package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad TipoInstitucion. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface TipoInstitucionRepository extends JpaRepository<TipoInstitucion,Integer>{

    Optional<TipoInstitucion> findByInstitucion(String institucion);
    
}
