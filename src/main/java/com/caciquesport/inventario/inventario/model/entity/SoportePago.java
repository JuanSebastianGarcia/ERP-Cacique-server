package com.caciquesport.inventario.inventario.model.entity;

import java.util.ArrayList;
import com.caciquesport.inventario.inventario.model.estados.EstadoSoportePago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un soporte de pago
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "soporte_pago")
public class SoportePago {

    // Identificador único del soporte de pago
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte")
    private int id;

    // Estado del soporte de pago
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_soporte", updatable = true, nullable = false)
    private EstadoSoportePago estadoSoporte;

    // Valor total pagado en el soporte de pago
    @Column(name = "valor_total_pagado", updatable = true, nullable = false)
    private double valorTotalPagado;

    // Lista de pagos asociados a este soporte de pago
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "soporte_pago_id",updatable = true) 
    private ArrayList<Pago> listaPagos;
}
