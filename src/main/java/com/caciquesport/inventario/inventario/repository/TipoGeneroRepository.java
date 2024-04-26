package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad TipoGenero. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface TipoGeneroRepository extends JpaRepository<TipoGenero,Integer> {

    Optional<TipoGenero> findByGenero(String genero);
}
