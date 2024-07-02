package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;

import com.caciquesport.inventario.inventario.model.configTypes.MetodoPago;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name="pago")
public class Pago {

    //_____________FIEDLS__________________//
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private int id;

    @Column(name="fecha_pago", nullable = false , updatable = false)
    private LocalDate fechaPago;

    @Column(name="valor_pago", nullable = false, updatable = false)
    private double valorPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name="metodo_pago",nullable = false, updatable = false)
    private MetodoPago MetodoPago;
}
