package com.caciquesport.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caciquesport.inventario.inventario.model.entity.ProductoFactura;

@Repository
public interface ProductoFacturaRepository extends JpaRepository<ProductoFactura,Integer>{

}
