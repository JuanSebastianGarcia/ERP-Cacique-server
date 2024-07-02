package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;

import com.caciquesport.inventario.inventario.model.estados.EstadoFactura;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase que representa una factura de venta
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="factura")
public class Factura {

    //_____________FIEDLS__________________//
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_factura")
    private int idFactura;

    @Column(name="fecha_factura",nullable = false,updatable = false)
    private LocalDate fechaFactura;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_factura",nullable = false,updatable = true)
    private EstadoFactura EstadoFactura; 

    @Column(name="valor_factura",nullable = false, updatable = true)
    private double valorFactura;
    

}
