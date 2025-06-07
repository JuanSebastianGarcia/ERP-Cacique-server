package com.caciquesport.inventario.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caciquesport.inventario.inventario.dto.RespuestaDto;
import com.caciquesport.inventario.inventario.dto.Entities.ConfigTypesDto;
import com.caciquesport.inventario.inventario.service.interfaces.TipoGeneroServicio;
import com.caciquesport.inventario.inventario.service.interfaces.TipoHorarioServicio;
import com.caciquesport.inventario.inventario.service.interfaces.TipoIstitucionServicio;
import com.caciquesport.inventario.inventario.service.interfaces.TipoPrendaServicio;
import com.caciquesport.inventario.inventario.service.interfaces.TipoTallaServicio;

import lombok.AllArgsConstructor;

import java.util.List;

/*
 * se encarga de la creacion, busqueda y eliminacion para los tipos de configuracion de los productos
 * provee una funcion para crear, eliminar, y listar para cada uno de los tipos
 * Institucion, prenda, talla, genero, horario
 */
@RestController
@RequestMapping("/api/configuracionTipos")
@AllArgsConstructor
public class ConfigTypesController {

    /*
     * servicios para la configuracion de los tipos
     */
    private final TipoPrendaServicio tipoPrendaServicioImpl;
    private final TipoIstitucionServicio tipoInstitucionServicioImpl;
    private final TipoHorarioServicio tipoHorarioServicioImpl;
    private final TipoGeneroServicio tipoGeneroServicioImpl;
    private final TipoTallaServicio tipoTallaServicioImpl;

    /*
     * Crear una prenda
     * 
     * @param nuevaPrenda - el nombre de la prenda que se va agregar
     * 
     * @return mensaje de confirmacion
     */
    @PostMapping("/prenda")
    public ResponseEntity<RespuestaDto<String>> crearPrenda(@RequestBody ConfigTypesDto nuevaPrenda)
            throws Exception {

        tipoPrendaServicioImpl.crearPrenda(nuevaPrenda);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "prenda agregada"));
    }

    /*
     * eliminar una prenda
     * 
     * @param prenda - identifiacador unico
     * 
     * @return mensaje de confirmacion
     */
    @DeleteMapping("/prenda/{prenda}")
    public ResponseEntity<RespuestaDto<String>> eliminarPrenda(@PathVariable("prenda") String prenda) throws Exception {

        tipoPrendaServicioImpl.eliminarPrenda(prenda);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "prenda eliminada"));
    }



    /*
     * buscar y retornar la lista de prendas
     */
    @GetMapping("/prenda")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> listarPrendas() throws Exception {

        List<ConfigTypesDto> lista = tipoPrendaServicioImpl.listarPrendas();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }



    /*
     * crear institucion
     * 
     * @param nuevaInstitucion - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @PostMapping("/institucion")
    public ResponseEntity<RespuestaDto<String>> crearInstitucion(@RequestBody ConfigTypesDto nuevaInstitucion) throws Exception {

        tipoInstitucionServicioImpl.crearInstitucion(nuevaInstitucion);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "institucion agregada"));
    }

    /*
     * eliminar institucion
     * 
     * @param institucion - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @DeleteMapping("/institucion/{institucion}")
    public ResponseEntity<RespuestaDto<String>> eliminarInstitucion(@PathVariable("institucion") String institucion)
            throws Exception {

        tipoInstitucionServicioImpl.eliminarInstitucion(institucion);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "institucion eliminada"));
    }


    
    /*
     * buscar y retornar la lista de instituciones
     * 
     */
    @GetMapping("/institucion")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarInstituciones() throws Exception {

        List<ConfigTypesDto> lista = tipoInstitucionServicioImpl.listarInstituciones();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

    
    /*
     * crear horario
     * 
     * @param nuevoHorario - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @PostMapping("/horario")
    public ResponseEntity<RespuestaDto<String>> crearHorario(@RequestBody ConfigTypesDto nuevoHorario)
            throws Exception {

        tipoHorarioServicioImpl.crearHorario(nuevoHorario);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "horario agregado"));
    }

    /*
     * eliminar horario
     * 
     * @param horario - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @DeleteMapping("/horario/{horario}")
    public ResponseEntity<RespuestaDto<String>> eliminarHorario(@PathVariable("horario") String horario)
            throws Exception {

        tipoHorarioServicioImpl.eliminarHorario(horario);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "horario eliminado"));
    }

    /*
     * buscar y retornar la lista de horarios
     * 
     * @return lista de datos de configuracion
     * 
     */
    @GetMapping("/horario")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarHorarios() throws Exception {

        List<ConfigTypesDto> lista = tipoHorarioServicioImpl.listarHorarios();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

    /*
     * crear genero
     * 
     * @param nuevoGenero - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @PostMapping("/genero")
    public ResponseEntity<RespuestaDto<String>> crearGenero(@RequestBody ConfigTypesDto nuevoGenero)
            throws Exception {

        tipoGeneroServicioImpl.crearGenero(nuevoGenero);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "género agregado"));
    }

    /*
     * eliminar genero
     * 
     * @param genero - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @DeleteMapping("/genero/{genero}")
    public ResponseEntity<RespuestaDto<String>> eliminarGenero(@PathVariable("genero") String genero) throws Exception {

        tipoGeneroServicioImpl.eliminarGenero(genero);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "género eliminado"));
    }


    
    /*
     * buscar y retornar la lista de géneros
     * 
     *
     * @return lista de datos de configuracion
     */
    @GetMapping("/genero")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarGeneros() throws Exception {

        List<ConfigTypesDto> lista = tipoGeneroServicioImpl.listarGeneros();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

    /*
     * crear talla
     * 
     * @param nuevaTalla - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @PostMapping("/talla")
    public ResponseEntity<RespuestaDto<String>> crearTalla(@RequestBody ConfigTypesDto nuevaTalla)
            throws Exception {

        tipoTallaServicioImpl.crearTalla(nuevaTalla);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "talla agregada"));
    }

    /*
     * eliminar talla
     * 
     * @param talla - nombre identificador unico
     * 
     * @return mensaje de confirmacion
     * 
     */
    @DeleteMapping("/talla/{talla}")
    public ResponseEntity<RespuestaDto<String>> eliminarTalla(@PathVariable("talla") String talla) throws Exception {

        tipoTallaServicioImpl.eliminarTalla(talla);

        return ResponseEntity.ok().body(new RespuestaDto<>(false, "talla eliminada"));
    }


    
    /*
     * buscar y retornar la lista de tallas
     * 
     * @return lista de datos de configuracion
     */
    @GetMapping("/talla")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarTallas() throws Exception {

        List<ConfigTypesDto> lista = tipoTallaServicioImpl.listarTallas();

        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

}
