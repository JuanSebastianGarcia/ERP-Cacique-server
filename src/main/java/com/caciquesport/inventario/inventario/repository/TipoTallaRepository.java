package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad TipoTalla. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface TipoTallaRepository extends JpaRepository<TipoTalla,Integer>{

    Optional<TipoTalla> findByTalla(String talla);
}
