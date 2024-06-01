package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.service.implementations.TipoPrendaServicioImpl;

import lombok.AllArgsConstructor;

import java.util.List;

/*
 * se encarga de la creacion, busqueda y eliminacion para los tipos de configuracion de los productos
 * provee una funcion para crear, eliminar, y listar para cada uno de los tipos
 * Institucion, prenda, talla, genero, horario
 */
@RestController()
@RequestMapping("/api/configuracionTipos")
@AllArgsConstructor
public class ConfigTypesController{

    /*
     * servicios para la configuracion de los tipos
     */
    private final TipoPrendaServicioImpl tipoPrendaServicioImpl;



    /*
     * Crear una prenda
     * @param nuevaPrenda - el nombre de la prenda que se va agregar
     */
    @PostMapping("/crearPrenda/{nuevaPrenda}")
    public ResponseEntity<RespuestaDto<String>> crearPrenda(@PathVariable("nuevaPrenda") String nuevaPrenda) throws Exception{

        tipoPrendaServicioImpl.crearPrenda(nuevaPrenda);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"prenda agregada"));
    }


    /*
     * eliminar una prenda
     * @param prenda - identifiacador unico
     */
    @PostMapping("/eliminarPrenda/{prenda}")
    public ResponseEntity<RespuestaDto<String>> eliminarPrenda(@PathVariable("prenda") String prenda) throws Exception{

        tipoPrendaServicioImpl.eliminarPrenda(prenda);

        return ResponseEntity.ok().body(new RespuestaDto<>(false,"prenda eliminada"));
    }


    
    /*
     * buscar y retornar la lista de prendas
     */
    @GetMapping("/buscarPrendas")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> listarPrendas() throws Exception{

        List<ConfigTypesDto> lista = tipoPrendaServicioImpl.listarPrendas();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }



    
}
