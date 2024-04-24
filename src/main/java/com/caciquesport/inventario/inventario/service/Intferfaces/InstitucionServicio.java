package com.caciquesport.inventario.inventario.service.Intferfaces;

import com.caciquesport.inventario.inventario.model.configTypes.Institucion;

public interface InstitucionServicio {

    int crearInstitucion(Institucion nuevaInstitucion);

    int actualizarInstitucion(Institucion institucion);

    void eliminarInstitucion(Institucion eliminarInstitucion);



}
