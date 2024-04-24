package com.caciquesport.inventario.inventario.service.Intferfaces;

import java.util.List;
import com.caciquesport.inventario.inventario.model.configTypes.Institucion;

public interface InstitucionServicio {

    Integer crearInstitucion(Institucion nuevaInstitucion) throws Exception;

    Integer actualizarInstitucion(Institucion institucion) throws Exception;

    void eliminarInstitucion(String nombreInstitucion) throws Exception;

    Institucion obtenerInstitucion(String nombreInstitucion) throws Exception;

    List<Institucion> listarInstituciones() throws Exception;
    
    

}
