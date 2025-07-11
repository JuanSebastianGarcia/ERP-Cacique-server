package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caciquesport.inventario.inventario.dto.FiltroProductoDto;
import com.caciquesport.inventario.inventario.dto.ProductoDto;
import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.service.interfaces.ProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

/*
 * Controlador encargado de brindar los servicios principales al jefe de inventario,
 * el controlador brindará los servicios para poder gestionar el inventario
 */
@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
@Tag(name = "Producto", description = "Operaciones para la gestión del inventario de productos")
public class ProductoController {

    /*
     * SERVICIOS 
     */
    private final ProductoServicio productoServicio;

    /**
     * API para la creación de un producto
     * 
     * @param nuevoProductoDto - contiene la información para la creación de un nuevo producto
     */
    @PostMapping
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el sistema de inventario.")
    public ResponseEntity<RespuestaDto<String>> crearProducto(@Valid @RequestBody ProductoDto nuevoProductoDto) throws Exception {
        Integer id = productoServicio.crearProducto(nuevoProductoDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "el producto ha sido creado id:" + id));
    }

    /**
     * API para la búsqueda de un producto
     * 
     * @param filtroProductoDto - contiene las características de diferenciación que un producto tiene con otro
     * @return - lista de productos filtrada
     */
    @GetMapping
    @Operation(summary = "Buscar productos", description = "Filtra los productos con base en los parámetros opcionales.")
    public ResponseEntity<RespuestaDto<List<ProductoDto>>> buscarProductos(
            @Parameter(description = "Tipo de prenda") @RequestParam(required = false) String prenda,
            @Parameter(description = "Talla del producto") @RequestParam(required = false) String talla,
            @Parameter(description = "Institución asociada") @RequestParam(required = false) String institucion,
            @Parameter(description = "Género del producto") @RequestParam(required = false) String genro,
            @Parameter(description = "Horario asociado") @RequestParam(required = false) String horario
    ) throws Exception {
        FiltroProductoDto filtroProductoDto = new FiltroProductoDto(prenda, talla, horario, genro, institucion);
        List<ProductoDto> listaProducto = productoServicio.filtrarListaProducto(filtroProductoDto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, listaProducto));
    }

    /**
     * API para eliminar un producto
     * 
     * @param idProducto - el id del producto que se va a eliminar
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente por su ID.")
    public ResponseEntity<RespuestaDto<String>> eliminarProducto(
            @Parameter(description = "ID del producto a eliminar") @PathVariable("id") Integer idProducto) throws Exception {
        productoServicio.eliminarProducto(idProducto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "el producto ha sido eliminado"));
    }

    /**
     * API para la actualización de un producto
     * 
     * @param productoDto - trae la información del objeto 
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente.")
    public ResponseEntity<RespuestaDto<String>> actualizarProducto(
            @Valid @RequestBody ProductoDto productoDto,
            @Parameter(description = "ID del producto a actualizar") @PathVariable("id") Integer idProducto) throws Exception {
        Integer id = productoServicio.actualizarProducto(productoDto, idProducto);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "el producto ha sido actualizado id:" + id));
    }
}
