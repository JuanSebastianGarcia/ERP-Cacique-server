package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;
import com.caciquesport.inventario.inventario.model.configTypes.MetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un pago
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pago")
public class Pago {

    // Identificador único del pago
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private int id;

    // Fecha en la que se realizó el pago
    @Column(name = "fecha_pago", nullable = false, updatable = false)
    private LocalDate fechaPago;

    // Valor del pago realizado
    @Column(name = "valor_pago", nullable = false, updatable = false)
    private double valorPago;

    // Método de pago utilizado
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, updatable = false)
    private MetodoPago metodoPago;
}

