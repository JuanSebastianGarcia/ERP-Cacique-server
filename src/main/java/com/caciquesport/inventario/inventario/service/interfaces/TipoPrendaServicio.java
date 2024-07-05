package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

/**
 * Interfaz para definir los servicios de gestión de TipoPrenda.
 * Proporciona métodos para operaciones CRUD básicas en TipoPrenda.
 */
public interface TipoPrendaServicio {

    /**
     * Crear una nueva prenda.
     *
     * @param nuevoTipoPrenda El objeto de tipo ConfigTypesDto que representa la nueva prenda a almacenar.
     */
    void crearPrenda(ConfigTypesDto nuevoTipoPrenda) throws Exception;

    /**
     * Eliminar una prenda por su identificador único.
     *
     * @param prenda El identificador único de la prenda a eliminar.
     */
    void eliminarPrenda(String prenda) throws Exception;

    /**
     * Buscar y obtener una prenda por su identificador único.
     *
     * @param prenda El identificador único de la prenda a obtener.
     * @return El objeto de tipo TipoPrenda que representa la prenda encontrada.
     */
    TipoPrenda obtenerPrenda(String prenda) throws Exception;

    /**
     * Obtener la lista de todas las prendas.
     *
     * @return Una lista de objetos ConfigTypesDto que representa todas las prendas.
     */
    List<ConfigTypesDto> listarPrendas() throws Exception;
}



