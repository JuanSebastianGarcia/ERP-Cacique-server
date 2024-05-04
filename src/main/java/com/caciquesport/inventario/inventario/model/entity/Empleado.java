package com.caciquesport.inventario.inventario.model.entity;

import java.io.Serializable;

import com.caciquesport.inventario.inventario.model.configTypes.TipoEmpleado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "empleado")
public class Empleado implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer id;

    @Column(name = "nombre_empleado" , nullable = false , length = 20 , updatable = false)
    private String nombre;

    @Column(name = "cedula_empleado" , nullable = false , length = 10 , updatable = false , unique = true)
    private String cedula;

    @Column(name = "telefono_empleado"  , length = 10 , unique = true)
    private String telefono;

    @Column(name = "email_empleado" , nullable = false , length = 40 , unique = true)
    private String email;

    @Column(name = "password_empleado" , nullable = false , length = 100 )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_empleado", nullable = false , updatable = false )
    private TipoEmpleado tipoEmpleado;

    
}
