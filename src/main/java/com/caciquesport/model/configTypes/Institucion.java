package com.caciquesport.model.configTypes;

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
@Table(name = "institucion")
public class Institucion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institucion")
    private int id;

    @Column(name = "institucion" , nullable = false , unique = true , updatable = false)
    private String institucion;




    
}
