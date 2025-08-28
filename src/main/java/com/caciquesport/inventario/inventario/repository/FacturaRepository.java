package com.caciquesport.inventario.inventario.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.Cliente;
import com.caciquesport.inventario.inventario.model.entity.Factura;


/**
 * Repositorio que se encarga de tener comunicacion hacia la base de datos para la entidad Factura. 
 * extendiende de jpaRepository para obtener los metodos crud basicos
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura,Integer>{


    /*
     * buscar una factura por medio del cliente
     */
    List<Factura> findByCliente(Cliente cliente);

    /*
     * Buscar facturas en un rango de fechas específico
     * 
     * @param fechaInicio fecha de inicio del rango (inclusive)
     * @param fechaFin fecha de fin del rango (inclusive)
     * @return lista de facturas dentro del rango de fechas
     */
    @Query("SELECT f FROM Factura f " +
           "WHERE f.fechaFactura >= :fechaInicio " +
           "AND f.fechaFactura <= :fechaFin " +
           "ORDER BY f.fechaFactura ASC")
    List<Factura> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                   @Param("fechaFin") LocalDate fechaFin);

}
