package com.coop8.demojwt.Models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DescripcionFijo extends DatosAuditoria {

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    // Constructor sin parámetros
    public DescripcionFijo() {
        super();
    }

    // Constructor con parámetros
    public DescripcionFijo(String descripcion, String usuariosys) {
        super(usuariosys);
        this.descripcion = descripcion;
    }
}