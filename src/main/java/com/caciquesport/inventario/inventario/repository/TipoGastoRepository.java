package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;

/**
 * Repositorio que se encarga de la comunicación con la base de datos para la entidad TipoGasto.
 * Extiende de JpaRepository para obtener los métodos CRUD básicos.
 */
@Repository
public interface TipoGastoRepository extends JpaRepository<TipoGasto, Integer> {

    /**
     * Busca un tipo de gasto por su nombre.
     * 
     * @param nombreTipoGasto El nombre del tipo de gasto a buscar.
     * @return Un Optional con el tipo de gasto encontrado, o empty si no se encuentra.
     */
    Optional<TipoGasto> findByNombre(String nombreTipoGasto);

}
