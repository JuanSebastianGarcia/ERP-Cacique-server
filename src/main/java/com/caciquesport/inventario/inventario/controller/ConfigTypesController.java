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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.util.List;

/*
 * Se encarga de la creación, búsqueda y eliminación para los tipos de configuración de los productos.
 * Provee una función para crear, eliminar y listar para cada uno de los tipos:
 * Institución, prenda, talla, género, horario.
 */
@RestController
@RequestMapping("/api/configuracionTipos")
@AllArgsConstructor
@Tag(name = "Configuración de Tipos", description = "Operaciones para gestionar tipos configurables de productos: prenda, institución, horario, género y talla")
public class ConfigTypesController {


    /*
    * Servicios para la configuración de los tipos
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
    * @return mensaje de confirmación
    */
    @PostMapping("/prenda")
    @Operation(summary = "Crear prenda", description = "Agrega un nuevo tipo de prenda al sistema.")
    public ResponseEntity<RespuestaDto<String>> crearPrenda(@RequestBody ConfigTypesDto nuevaPrenda) throws Exception {
        tipoPrendaServicioImpl.crearPrenda(nuevaPrenda);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "prenda agregada"));
    }

    /*
    * Eliminar una prenda
    * 
    * @param prenda - identificador único
    * 
    * @return mensaje de confirmación
    */
    @DeleteMapping("/prenda/{prenda}")
    @Operation(summary = "Eliminar prenda", description = "Elimina una prenda existente por su identificador.")
    public ResponseEntity<RespuestaDto<String>> eliminarPrenda(
            @Parameter(description = "Nombre o identificador de la prenda") 
            @PathVariable("prenda") String prenda) throws Exception {
        tipoPrendaServicioImpl.eliminarPrenda(prenda);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "prenda eliminada"));
    }

    /*
    * Buscar y retornar la lista de prendas
    */
    @GetMapping("/prenda")
    @Operation(summary = "Listar prendas", description = "Devuelve la lista completa de prendas configuradas.")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> listarPrendas() throws Exception {
        List<ConfigTypesDto> lista = tipoPrendaServicioImpl.listarPrendas();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }




    /*
    * Crear institución
    * 
    * @param nuevaInstitucion - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @PostMapping("/institucion")
    @Operation(summary = "Crear institución", description = "Agrega una nueva institución al sistema.")
    public ResponseEntity<RespuestaDto<String>> crearInstitucion(@RequestBody ConfigTypesDto nuevaInstitucion) throws Exception {
        tipoInstitucionServicioImpl.crearInstitucion(nuevaInstitucion);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "institución agregada"));
    }

    /*
    * Eliminar institución
    * 
    * @param institucion - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @DeleteMapping("/institucion/{institucion}")
    @Operation(summary = "Eliminar institución", description = "Elimina una institución existente por su identificador.")
    public ResponseEntity<RespuestaDto<String>> eliminarInstitucion(
            @Parameter(description = "Nombre o identificador de la institución") 
            @PathVariable("institucion") String institucion) throws Exception {
        tipoInstitucionServicioImpl.eliminarInstitucion(institucion);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "institución eliminada"));
    }

    /*
    * Buscar y retornar la lista de instituciones
    */
    @GetMapping("/institucion")
    @Operation(summary = "Listar instituciones", description = "Devuelve la lista completa de instituciones configuradas.")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarInstituciones() throws Exception {
        List<ConfigTypesDto> lista = tipoInstitucionServicioImpl.listarInstituciones();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

    
    /*
    * Crear horario
    * 
    * @param nuevoHorario - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @PostMapping("/horario")
    @Operation(summary = "Crear horario", description = "Agrega un nuevo horario al sistema.")
    public ResponseEntity<RespuestaDto<String>> crearHorario(@RequestBody ConfigTypesDto nuevoHorario) throws Exception {
        tipoHorarioServicioImpl.crearHorario(nuevoHorario);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "horario agregado"));
    }

    /*
    * Eliminar horario
    * 
    * @param horario - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @DeleteMapping("/horario/{horario}")
    @Operation(summary = "Eliminar horario", description = "Elimina un horario existente por su identificador.")
    public ResponseEntity<RespuestaDto<String>> eliminarHorario(
            @Parameter(description = "Nombre o identificador del horario")
            @PathVariable("horario") String horario) throws Exception {
        tipoHorarioServicioImpl.eliminarHorario(horario);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "horario eliminado"));
    }

    /*
    * Buscar y retornar la lista de horarios
    * 
    * @return lista de datos de configuración
    */
    @GetMapping("/horario")
    @Operation(summary = "Listar horarios", description = "Devuelve la lista completa de horarios configurados.")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarHorarios() throws Exception {
        List<ConfigTypesDto> lista = tipoHorarioServicioImpl.listarHorarios();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }

    /*
    * Crear género
    * 
    * @param nuevoGenero - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @PostMapping("/genero")
    @Operation(summary = "Crear género", description = "Agrega un nuevo género al sistema.")
    public ResponseEntity<RespuestaDto<String>> crearGenero(@RequestBody ConfigTypesDto nuevoGenero) throws Exception {
        tipoGeneroServicioImpl.crearGenero(nuevoGenero);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "género agregado"));
    }

    /*
    * Eliminar género
    * 
    * @param genero - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @DeleteMapping("/genero/{genero}")
    @Operation(summary = "Eliminar género", description = "Elimina un género existente por su identificador.")
    public ResponseEntity<RespuestaDto<String>> eliminarGenero(
            @Parameter(description = "Nombre o identificador del género")
            @PathVariable("genero") String genero) throws Exception {
        tipoGeneroServicioImpl.eliminarGenero(genero);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "género eliminado"));
    }

    /*
    * Buscar y retornar la lista de géneros
    *
    * @return lista de datos de configuración
    */
    @GetMapping("/genero")
    @Operation(summary = "Listar géneros", description = "Devuelve la lista completa de géneros configurados.")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarGeneros() throws Exception {
        List<ConfigTypesDto> lista = tipoGeneroServicioImpl.listarGeneros();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }


    /*
    * Crear talla
    * 
    * @param nuevaTalla - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @PostMapping("/talla")
    @Operation(summary = "Crear talla", description = "Agrega una nueva talla al sistema.")
    public ResponseEntity<RespuestaDto<String>> crearTalla(@RequestBody ConfigTypesDto nuevaTalla) throws Exception {
        tipoTallaServicioImpl.crearTalla(nuevaTalla);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "talla agregada"));
    }

    /*
    * Eliminar talla
    * 
    * @param talla - nombre identificador único
    * 
    * @return mensaje de confirmación
    */
    @DeleteMapping("/talla/{talla}")
    @Operation(summary = "Eliminar talla", description = "Elimina una talla existente por su identificador.")
    public ResponseEntity<RespuestaDto<String>> eliminarTalla(
            @Parameter(description = "Nombre o identificador de la talla")
            @PathVariable("talla") String talla) throws Exception {
        tipoTallaServicioImpl.eliminarTalla(talla);
        return ResponseEntity.ok().body(new RespuestaDto<>(false, "talla eliminada"));
    }

    /*
    * Buscar y retornar la lista de tallas
    * 
    * @return lista de datos de configuración
    */
    @GetMapping("/talla")
    @Operation(summary = "Listar tallas", description = "Devuelve la lista completa de tallas configuradas.")
    public ResponseEntity<RespuestaDto<List<ConfigTypesDto>>> buscarTallas() throws Exception {
        List<ConfigTypesDto> lista = tipoTallaServicioImpl.listarTallas();
        return ResponseEntity.ok().body(new RespuestaDto<>(false, lista));
    }


}
