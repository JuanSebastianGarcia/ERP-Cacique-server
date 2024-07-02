package com.caciquesport.inventario.inventario.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="cliente")
public class Cliente {

    //_____________FIEDLS__________________//
    @Id
    @Column(name="cedula_cliente", nullable = false, updatable = false)
    private String cedula;

    @Column(name="nombre_cliente",nullable = false, updatable = false)
    private String nombre;

    @Column(name="telefono_cliente",nullable = false,updatable = true)
    private String telefono;

    @Column(name="email_cliente",nullable = true,updatable = true)
    private String email;

    @Column(name="direccion_cliente",nullable = true,updatable = true)
    private String direccion;
}
