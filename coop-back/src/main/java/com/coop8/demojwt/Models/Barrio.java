package com.coop8.demojwt.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "barrios")
public class Barrio extends DescripcionFijo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_barrio")
    private Integer codBarrio;


    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;


    @Column(name = "codigo_set")
    private Integer codigoSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad", foreignKey = @ForeignKey(name = "fk_barrio_ciudad"))
    private Ciudad ciudad;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "usuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario"))
//    private Usuarios usuario;
}
