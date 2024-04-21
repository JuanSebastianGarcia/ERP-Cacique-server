package com.caciquesport.model.entity;

import com.caciquesport.model.configTypes.TipoPrenda;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="producto")
public class Producto {

    private int id;
    
    @ManyToOne
    private TipoPrenda tipoPrenda;



}
