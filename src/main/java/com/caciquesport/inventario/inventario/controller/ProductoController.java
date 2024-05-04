package com.caciquesport.inventario.inventario.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.implementations.ProductoServicioImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;


/*
 * Controlador encargado de brindar los servicios principales al jefe de inventario, el controlador brindara los 
 * servicios para poder gestionar el inventario
 */
@RestController
@RequestMapping("/api/manejoProducto")
@RequiredArgsConstructor
public class ProductoController {


    /*
     * SERVICIOS 
     */
    private final ProductoServicioImpl productoServicioImpl;




    /*
     * controlador para la creacion de un producto
     * 
     */
    @PostMapping("/crearProducto")
    public ResponseEntity<RespuestaDto<String>> crearProducto(@Valid @RequestBody ProductoDto nuevoProductoDto) throws Exception{

        productoServicioImpl.crearProducto(nuevoProductoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el producto ha sido creado"));
    }



    /*
     * controlador para la busqueda de un producto
     * 
     * @param filtroProductoDto - contiene las catacteristicas de diferenciacion que un producto tiene con otro
     * 
     * @return - lista de productos filtrada
     */
    @GetMapping("/buscarProductos")
    public ResponseEntity<RespuestaDto<List<ProductoDto>>> buscarProductos(@RequestBody FiltroProductoDto filtroProductoDto ) throws Exception{

        List<ProductoDto> listaProducto = productoServicioImpl.filtrarListaProducto(filtroProductoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,listaProducto));
    }

}
