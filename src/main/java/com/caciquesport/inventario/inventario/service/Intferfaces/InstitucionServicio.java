package com.caciquesport.inventario.inventario.service.Intferfaces;



import java.util.List;

import com.caciquesport.inventario.inventario.model.configTypes.Institucion;

public interface InstitucionServicio {

    int crearInstitucion(Institucion nuevaInstitucion);

    int actualizarInstitucion(Institucion institucion);

    void eliminarInstitucion(String nombreInstitucion);

    Institucion obtenerInstitucion(String nombreInstitucion);

    List<Institucion> listarInstituciones();
    

}
