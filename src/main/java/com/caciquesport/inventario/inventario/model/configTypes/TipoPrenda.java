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
@Table(name = "tipo_prenda")
public class TipoPrenda {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenda")
    private int id;

    @Column(name = "prenda" , nullable = false , unique = true , length = 30 , updatable = false)
    private String prenda;






}