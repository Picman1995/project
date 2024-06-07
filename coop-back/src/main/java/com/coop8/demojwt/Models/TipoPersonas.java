package com.coop8.demojwt.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "tipo_personas")
public class TipoPersonas extends DescripcionFijo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_personas",columnDefinition = "SERIAL")
    private Integer codTipoPersonas;

// Constructor sin parámetros
    public TipoPersonas() {
        super();
    }

    // Constructor con parámetros
    public TipoPersonas(Integer codTipoPersonas, String descripcion, String usuariosys) {
        super(descripcion, usuariosys);
        this.codTipoPersonas = codTipoPersonas;
    }
}
