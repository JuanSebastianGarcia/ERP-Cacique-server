package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;

/**
 * Interfaz para definir los servicios de gestión de TipoTalla.
 * Proporciona métodos para operaciones CRUD básicas en TipoTalla.
 */
public interface TipoTallaServicio {

    /**
     * Crear una nueva talla.
     *
     * @param nuevoTipoTalla El objeto de tipo ConfigTypesDto que representa la nueva talla a almacenar.
     */
    void crearTalla(ConfigTypesDto nuevoTipoTalla) throws Exception;

    /**
     * Eliminar una talla por su identificador único.
     *
     * @param talla El identificador único de la talla a eliminar.
     */
    void eliminarTalla(String talla) throws Exception;

    /**
     * Buscar y obtener una talla por su identificador único.
     *
     * @param talla El identificador único de la talla a obtener.
     * @return El objeto de tipo TipoTalla que representa la talla encontrada.
     */
    TipoTalla obtenerTalla(String talla) throws Exception;

    /**
     * Obtener la lista de todas las tallas.
     *
     * @return Una lista de objetos ConfigTypesDto que representa todas las tallas.
     */
    List<ConfigTypesDto> listarTallas() throws Exception;
}

