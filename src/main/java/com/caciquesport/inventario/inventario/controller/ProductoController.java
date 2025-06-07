package com.caciquesport.inventario.inventario.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoServicio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;


/*
 * Controlador encargado de brindar los servicios principales al jefe de inventario, el controlador brindara los 
 * servicios para poder gestionar el inventario
 */
@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {


    /*
     * SERVICIOS 
     */
    private final ProductoServicio productoServicio;




    /**
     * api para la creacion de un producto
     * 
     * @param nuevoProductoDto - contiene la informacion para la creacion de un nuevo producto
     */
    @PostMapping
    public ResponseEntity<RespuestaDto<String>> crearProducto(@Valid @RequestBody ProductoDto nuevoProductoDto) throws Exception{

        Integer id =productoServicio.crearProducto(nuevoProductoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el producto ha sido creado id:"+id));
    }



    /**
     * api  para la busqueda de un producto
     * 
     * @param filtroProductoDto - contiene las catacteristicas de diferenciacion que un producto tiene con otro
     * 
     * @return - lista de productos filtrada
     */
    @GetMapping
    public ResponseEntity<RespuestaDto<List<ProductoDto>>> buscarProductos(
            @RequestParam(required = false) String prenda,
            @RequestParam(required = false) String talla,
            @RequestParam(required = false) String institucion,
            @RequestParam(required = false) String genro,
            @RequestParam(required = false) String horario
    ) throws Exception{

        FiltroProductoDto filtroProductoDto = new FiltroProductoDto(prenda, talla, horario, genro, institucion);
        List<ProductoDto> listaProducto = productoServicio.filtrarListaProducto(filtroProductoDto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,listaProducto));
    }

    
    /**
     * api para eliminar un producto
     * 
     * @param idProducto - el id del producto que se va a eliminar
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDto<String>> eliminarProducto(@PathVariable("id") Integer idProducto) throws Exception{

        productoServicio.eliminarProducto(idProducto);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el producto ha sido eliminado"));
    }




    /**
     * api para la actualizacion de un producto
     * 
     * @param productoDto - trae la informacion del objeto 
     */
    @PostMapping("/{id}")
    public ResponseEntity<RespuestaDto<String>> actualizarProducto(@Valid @RequestBody ProductoDto productoDto, @PathVariable("id") Integer idProducto) throws Exception{
        

        Integer id=productoServicio.actualizarProducto(productoDto, idProducto);
        
        return ResponseEntity.ok().body(new RespuestaDto<>(false,"el producto ha sido actualizado id:"+id));
    }







}
