package com.caciquesport.model.configTypes;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_prenda")
public class TipoPrenda {

    @Id
    private int id;

    private String tipoPrenda;






}
