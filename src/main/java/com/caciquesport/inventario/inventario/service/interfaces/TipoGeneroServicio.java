package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;

/**
 * Interfaz para definir los servicios de gestión de TipoGenero.
 * Proporciona métodos para operaciones CRUD básicas en TipoGenero.
 */
public interface TipoGeneroServicio {

    /**
     * Crear un nuevo género.
     *
     * @param nuevoTipoGenero El objeto de tipo ConfigTypesDto que representa el nuevo género a almacenar.
     */
    void crearGenero(ConfigTypesDto nuevoTipoGenero) throws Exception;

    /**
     * Buscar y obtener un género por su identificador único.
     *
     * @param genero El identificador único del género a obtener.
     * @return El objeto de tipo TipoGenero que representa el género encontrado.
     */
    TipoGenero obtenerGenero(String genero) throws Exception;

    /**
     * Eliminar un género por su identificador único.
     *
     * @param genero El identificador único del género a eliminar.
     */
    void eliminarGenero(String genero) throws Exception;

    /**
     * Obtener la lista de todos los géneros.
     *
     * @return Una lista de objetos ConfigTypesDto que representa todos los géneros.
     */
    List<ConfigTypesDto> listarGeneros() throws Exception;
}

