package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGasto;

/**
 * Repositorio que se encarga de la comunicación con la base de datos para la entidad TipoGasto.
 * Extiende de JpaRepository para obtener los métodos CRUD básicos.
 */
@Repository
public interface TipoGastoRepository extends JpaRepository<TipoGasto, Integer> {

    // Aquí se pueden agregar métodos personalizados de consulta si es necesario

}
