package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;

/**
 * Interfaz para definir los servicios de gestión de Institucion.
 * Proporciona métodos para operaciones CRUD básicas en Institucion.
 */
public interface TipoIstitucionServicio {

    Integer crearInstitucion(TipoInstitucion nuevoTipoInstitucion) throws Exception;

    Integer actualizarInstitucion(TipoInstitucion tipoInstitucion) throws Exception;

    void eliminarInstitucion(String institucion) throws Exception;

    TipoInstitucion obtenerInstitucion(String institucion) throws Exception;

    List<TipoInstitucion> listarInstituciones() throws Exception;
    
    

}
