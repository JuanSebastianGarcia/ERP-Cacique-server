package com.caciquesport.inventario.inventario.service.Intferfaces;

import java.util.List;

public interface TipoPrendaServicio {

    Integer crearTipoPrenda(TipoHorarioServicio nuevoTipoHorario) throws Exception;

    Integer actualizarTipoPrenda(TipoPrendaServicio tipoPrenda) throws Exception;

    Integer eliminarTipoPrenda(String tipoPrenda) throws Exception;

    TipoPrendaServicio  obtenerTipoPrenda(String tipoPrenda) throws Exception;

    List<TipoPrendaServicio> listarPrendas() throws Exception;

    
}
