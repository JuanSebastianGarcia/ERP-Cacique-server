package com.caciquesport.inventario.inventario.model.entity;

import com.caciquesport.inventario.inventario.model.estados.EstadoProducto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "producto_factura")
public class ProductoFactura {

    // Identificador único de la entidad ProductoFactura
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Identificación de la factura asociada
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    // Identificación del producto asociado
    @Column(name = "producto_id", nullable = false)
    private int producto;

    // Estado del producto en la factura
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_producto", nullable = false)
    private EstadoProducto estadoProducto;

}

