package com.caciquesport.inventario.inventario.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import com.caciquesport.inventario.inventario.model.estados.EstadoFactura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una factura de venta
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "factura")
public class Factura {

    // Identificador único de la factura
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    // Fecha en la que se generó la factura
    @Column(name = "fecha_factura", nullable = false, updatable = false)
    private LocalDate fechaFactura;

    // Estado actual de la factura
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_factura", nullable = false, updatable = true)
    private EstadoFactura estadoFactura;

    // Valor total de la factura
    @Column(name = "valor_factura", nullable = false, updatable = true)
    private double valorFactura;

    // Cliente asociado a la factura
    @ManyToOne
    @JoinColumn(name = "cliente_factura", nullable = false ,  updatable = false)
    private Cliente cliente;

    // Soporte de pago asociado a la factura
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "soporte_pago_factura" , nullable = false , updatable = true)
    private SoportePago soportePago;

    // Empleado que realizó la factura
    @ManyToOne
    @JoinColumn(name = "empleado_factura",nullable = false , updatable = false)
    private Empleado empleado;

    // Lista de productos asociados a la factura
    @ManyToMany
    @JoinTable(
        name = "lista_producto",
        joinColumns = @JoinColumn(name = "factura_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private ArrayList<Producto> listaProductos;
}
