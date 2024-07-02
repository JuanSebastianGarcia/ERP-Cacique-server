package com.caciquesport.inventario.inventario.model.entity;

import com.caciquesport.inventario.inventario.model.estados.EstadoSoportePago;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="soporte_pago")
public class SoportePago {

    //_____________FIEDLS__________________//
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_soporte")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_soporte", updatable = true)
    private EstadoSoportePago EstadoSoporte;

    @Column(name="valor_total_pagado", updatable = true)
    private double valorTotalPagado;
}
