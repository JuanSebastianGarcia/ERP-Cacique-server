package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.caciquesport.inventario.inventario.model.entity.Producto;
import java.util.Optional;

/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Producto. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{

   /*
    * Consultar un objeto por 5 atributos especificos, en donde todos tiene que ser iguales 
    */
    @Query("select p.tipo_prenda, p.tipo_horario, p.tipo_talla, p.tipo_institucion,p.tipo_genero from producto where p.tipo_institucion=?1 and p.tipo_talla=?2 and p.tipo_genero=?3 and p.tipo_horario=?4 and p.tipo_prenda=?5")
    Optional<Producto> verificarExistenciaProducto(Integer idInstitucion,Integer idTalla,Integer idGenero,
    Integer idhorario,Integer prenda);

}
