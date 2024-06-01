package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;

/**
 * Interfaz para definir los servicios de gestión de TipoGenero.
 * Proporciona métodos para operaciones CRUD básicas en TipoGenero.
 */
public interface TipoGeneroServicio {

    /*
    * crear un genero
    * @param nuevoTipoGenero - objeto a almacenar
    */
    void crearGenero(String nuevoTipoGenero) throws Exception;

    /*
    *buscar y optener  un genero 
    *@param  genero - identificador unico 
    */ 
    TipoGenero obtenerGenero(String genero) throws Exception ;

    /*
     * eliminar un genero
     * @param genero - identificador unico 
     */
    void eliminarGenero(String genero) throws Exception;

    /*
     * obtener la lista de generos
     */
    List<ConfigTypesDto> listarGeneros() throws Exception;
}
