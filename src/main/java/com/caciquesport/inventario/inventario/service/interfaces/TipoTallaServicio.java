package com.caciquesport.inventario.inventario.service.interfaces;


import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;

/**
 * Interfaz para definir los servicios de gestión de TipoTalla.
 * Proporciona métodos para operaciones CRUD básicas en TipoTalla.
 */
public interface TipoTallaServicio {

    /*
    * crear una talla
    * @param nuevoTipoTalla - objeto que se requiere almacenar
    */
    void crearTalla(ConfigTypesDto nuevoTipoTalla) throws Exception;

    /*
    * buscar una talla
    * @param talla - identificador unico
    */
    void eliminarTalla(String talla) throws Exception;

    /*
    * buscar una talla
    * @param talla - identificador unico
    */
    TipoTalla obtenerTalla(String talla) throws Exception;

   
    /*
    * buscar la lista de tallas
    */    
    List<ConfigTypesDto> listarTallas() throws Exception;
}
