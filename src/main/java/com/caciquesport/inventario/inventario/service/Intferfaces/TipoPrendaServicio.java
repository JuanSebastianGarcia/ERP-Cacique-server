package com.caciquesport.inventario.inventario.service.Intferfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

public interface TipoPrendaServicio {

    Integer crearPrenda(TipoPrenda nuevoTipoPrenda) throws Exception;

    Integer actualizarPrenda(TipoPrenda tipoPrenda) throws Exception;

    void eliminarPrenda(String prenda) throws Exception;

    TipoPrenda  obtenerPrenda(String prenda) throws Exception;

    List<TipoPrenda> listarPrendas() throws Exception;

    
}
