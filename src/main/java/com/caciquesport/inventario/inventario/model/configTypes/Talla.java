package com.caciquesport.inventario.inventario.model.configTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "talla")
public class Talla {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    private int id;

    @Column(name = "talla" , nullable = false , unique = true , length = 5 , updatable = false)
    private String talla;
}
