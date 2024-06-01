package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

/**
 * Interfaz para definir los servicios de gestión de TipoPrenda.
 * Proporciona métodos para operaciones CRUD básicas en TipoPrenda.
 */
public interface TipoPrendaServicio {

    /*
    * crear una prenda
    * @param nuevoTipoPrenda - objeto a almacenar
    */
    void crearPrenda(String nuevoTipoPrenda) throws Exception;

    /*
    * eliminar una prenda
    * @param prenda - identificador unico
    */
    void eliminarPrenda(String prenda) throws Exception;

    /*
    * buscar una prenda
    * @param prenda - identificador unico
    */
    TipoPrenda  obtenerPrenda(String prenda) throws Exception;

    /*
    * buscar la lista de prendas
    * @param prenda - identificador unico
    */
    List<ConfigTypesDto> listarPrendas() throws Exception;

    
}
