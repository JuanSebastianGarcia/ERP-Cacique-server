package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;

/**
 * Interfaz para definir los servicios de gestión de Institucion.
 * Proporciona métodos para operaciones CRUD básicas en Institucion.
 */
public interface TipoIstitucionServicio {

/**
     * Crear una nueva institución.
     *
     * @param nuevaInstitucion El objeto de tipo ConfigTypesDto que representa la nueva institución a almacenar.
     */
    void crearInstitucion(ConfigTypesDto nuevaInstitucion) throws Exception;

    /**
     * Eliminar una institución por su identificador único.
     *
     * @param institucion El identificador único de la institución a eliminar.
     */
    void eliminarInstitucion(String institucion) throws Exception;

    /**
     * Buscar y obtener una institución por su identificador único.
     *
     * @param institucion El identificador único de la institución a obtener.
     * @return El objeto de tipo TipoInstitucion que representa la institución encontrada.
     */
    TipoInstitucion obtenerInstitucion(String institucion) throws Exception;

    /**
     * Obtener la lista de todas las instituciones.
     *
     * @return Una lista de objetos ConfigTypesDto que representa todas las instituciones.
     */
    List<ConfigTypesDto> listarInstituciones() throws Exception;

}
