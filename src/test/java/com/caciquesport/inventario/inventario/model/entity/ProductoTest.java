package com.caciquesport.inventario.inventario.model.entity;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;
import com.caciquesport.inventario.inventario.service.implementations.TipoInstitucionServicioImpl;


@DataJpaTest
public class ProductoTest {

    /*
     * servicios
     */
    @Autowired
    private  TipoInstitucionServicioImpl tipoInstitucionServicioImpl;


    /*
     * metodo para probar la creacion de un producto atravez de su servicio
     *
    @Test
    public void crearBuscarProducto() throws Exception{

        List<TipoInstitucion> lista= tipoInstitucionServicioImpl.listarInstituciones();

        for (TipoInstitucion tipoInstitucion : lista) {
            System.out.println(tipoInstitucion.getInstitucion());
        }

        //ProductoDto producto=new ProductoDto("", null, null, null, null, 0, 0, null)
        //productoServicioImpl.crearProducto(null);

    }*/

}
