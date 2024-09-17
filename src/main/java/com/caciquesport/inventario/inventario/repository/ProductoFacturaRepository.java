package com.caciquesport.inventario.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.ProductoFactura;
import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;

@Repository
public interface ProductoFacturaRepository extends JpaRepository<ProductoFactura,Integer>{

    // Método para buscar todos los registros con estado pendiente
    List<ProductoFactura> findByEstadoProducto(EstadoProducto estadoProducto);
}
