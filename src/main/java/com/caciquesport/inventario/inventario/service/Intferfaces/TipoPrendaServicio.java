package com.caciquesport.inventario.inventario.service.Intferfaces;

import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;

public interface TipoPrendaServicio {

    Integer crearTipoPrenda(TipoPrenda nuevoTipoHorario) throws Exception;

    Integer actualizarTipoPrenda(TipoPrenda tipoPrenda) throws Exception;

    void eliminarTipoPrenda(String prenda) throws Exception;

    TipoPrenda  obtenerTipoPrenda(String prenda) throws Exception;

    List<TipoPrenda> listarPrendas() throws Exception;

    
}
