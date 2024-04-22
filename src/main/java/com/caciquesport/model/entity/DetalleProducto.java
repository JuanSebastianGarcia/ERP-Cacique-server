package com.caciquesport.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "detalle_producto")
public class DetalleProducto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_producto")
    private int id;

    @Column(name = "cantidad_producto" , nullable = false)
    private int cantidad;

    @Column(name = "precio_producto" , nullable = false)
    private double precio;

    @Column(name = "descripcion_producto" , nullable = false)
    private String descripcion;

    @OneToOne(mappedBy = "detalleProducto")
    private Producto producto;

}
