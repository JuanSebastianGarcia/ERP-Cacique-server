package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;

/**
 * Interfaz para definir los servicios de gestión de Institucion.
 * Proporciona métodos para operaciones CRUD básicas en Institucion.
 */
public interface TipoIstitucionServicio {

    /*
    * crear una institucion
    * @param nuevoTipoInstitucion - objeto a almacenar
    */
    void crearInstitucion(String nuevaInstitucion) throws Exception;

    /*
     * eliminar una institucion
     * @param institucion - identificador unico
     */
    void eliminarInstitucion(String institucion) throws Exception;

     /*
     * buscar una institucion
     * @param institucion - identificador unico
     */
    TipoInstitucion obtenerInstitucion(String institucion) throws Exception;

    /*
     * buscar la lista de instituciones
     */
    List<ConfigTypesDto> listarInstituciones() throws Exception;
    
    

}
