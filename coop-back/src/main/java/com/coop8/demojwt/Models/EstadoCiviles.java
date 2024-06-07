package com.coop8.demojwt.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado_civiles")
public class EstadoCiviles extends DescripcionFijo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_estado_civiles",columnDefinition = "SERIAL")
    private Integer codEstadoCiviles;
 
// Constructor sin parámetros
    public EstadoCiviles() {
        super();
    }

    // Constructor con parámetros
    public EstadoCiviles(Integer codEstadoCiviles, String descripcion, String usuariosys) {
        super(descripcion, usuariosys);
        this.codEstadoCiviles = codEstadoCiviles;
    }
}
