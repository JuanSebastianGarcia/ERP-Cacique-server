package com.caciquesport.inventario.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.configTypes.TipoGenero;
import com.caciquesport.inventario.inventario.model.configTypes.TipoHorario;
import com.caciquesport.inventario.inventario.model.configTypes.TipoInstitucion;
import com.caciquesport.inventario.inventario.model.configTypes.TipoPrenda;
import com.caciquesport.inventario.inventario.model.configTypes.TipoTalla;
import com.caciquesport.inventario.inventario.model.entity.Producto;


/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Producto. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{

   /*
    * Consultar un objeto por 5 atributos especificos, en donde todos tiene que ser iguales 
    */
    @Query("SELECT p FROM Producto p WHERE p.tipoInstitucion = :institucion AND p.tipoTalla = :talla AND p.tipoGenero = :genero AND p.tipoHorario = :horario AND p.tipoPrenda = :prenda")
    Optional<Producto> verificarExistenciaProducto(
    @Param("institucion") TipoInstitucion institucion, 
    @Param("talla") TipoTalla talla, 
    @Param("genero") TipoGenero genero, 
    @Param("horario") TipoHorario horario, 
    @Param("prenda") TipoPrenda prenda);


}
