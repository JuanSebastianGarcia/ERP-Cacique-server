package com.caciquesport.inventario.inventario.repository;

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
     * Buscar facturas por año y mes específico
     * 
     * @param year
     * @param month
     * @return
     */
    @Query("SELECT f FROM Factura f " +
           "WHERE FUNCTION('YEAR', f.fechaFactura) = :year " +
           "AND FUNCTION('MONTH', f.fechaFactura) = :month")
    List<Factura> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

}
