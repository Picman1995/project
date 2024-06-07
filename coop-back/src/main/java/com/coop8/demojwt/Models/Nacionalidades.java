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
@Table(name = "nacionalidades")
public class Nacionalidades extends DescripcionFijo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nacionalidades", columnDefinition = "SERIAL")
    private Integer id;

    // Constructor sin parámetros
    public Nacionalidades() {
        super();
    }

    // Constructor con parámetros
    public Nacionalidades(Integer id, String descripcion, String usuariosys) {
        super(descripcion, usuariosys);
        this.id = id;
    }
}
