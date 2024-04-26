package com.caciquesport.inventario.inventario.service.interfaces;

import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

/**
 * Interfaz para definir los servicios de gestión de TipoPrenda.
 * Proporciona métodos para operaciones CRUD básicas en TipoPrenda.
 */
public interface TipoPrendaServicio {

    Integer crearPrenda(TipoPrenda nuevoTipoPrenda) throws Exception;

    Integer actualizarPrenda(TipoPrenda tipoPrenda) throws Exception;

    void eliminarPrenda(String prenda) throws Exception;

    TipoPrenda  obtenerPrenda(String prenda) throws Exception;

    List<TipoPrenda> listarPrendas() throws Exception;

    
}
