package com.caciquesport.inventario.inventario.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoFacturaDto;
import com.caciquesport.inventario.inventario.model.entity.Factura;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoFacturaServicio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoFacturaServicioImpl implements ProductoFacturaServicio {


    /*
     * Servicio
     */
    private final ProductoServicioImpl productoServicioImpl;


    /**
     *recorrer la lista de productos uno a uno, los busca en la base de datos y agrega la instancia a una nueva lista que sera
     *agregada a la factura
     *
     * @param listaProductos - contiene la informacion necesaria de los productos
     * @param factura - instancia de la factura a la que se le agregara la lista 
     */
    @Override
    public void generarListaProductos(List<ProductoFacturaDto> listaProductosDto, Factura factura) throws Exception {

        List<Producto> listaObjetosProducto = new ArrayList<>();


        for (ProductoFacturaDto producto : listaProductosDto) {

            //buscar el objeto producto
            Producto productoEncontrado = productoServicioImpl.buscarObjetoProducto(
                new FiltroProductoDto(
                    producto.prenda(), producto.talla(), producto.horario(), producto.genero(), producto.institucion())
            );

            //agregar el objeto a la lista
            listaObjetosProducto.add(productoEncontrado);
        }

        agregarProductosAFactura(listaObjetosProducto,factura);
    }


    /**
     * Este metodo toma una lista de objetos de productos, extrae su id y lo agrega a una relacion entre una factura y producto.
     * esta lista de relaciones sera agregada a la factura para representar cuales productos contiene
     * 
     * @param listaObjetosProducto - contiene las instancias de los objetos encontrados
     * @param factura - instancia de la factura 
     */
    private void agregarProductosAFactura(List<Producto> listaObjetosProducto, Factura factura) {


        //se deben de crear las relaciones



    }




}
