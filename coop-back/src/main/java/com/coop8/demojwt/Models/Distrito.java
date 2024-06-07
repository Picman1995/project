 package com.coop8.demojwt.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "distritos", uniqueConstraints = {
    @UniqueConstraint(name = "uk_ditritos_codigo_sicoop", columnNames = "codigo_sicoop")
})
public class Distrito extends DescripcionFijo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distrito")
    private Integer codDistrito;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;

 

    @Column(name = "codigo_set")
    private Integer codigoSet;

    @Column(name = "codigo_sicoop", unique = true)
    private Integer codigoSicoop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id_departamento", foreignKey = @ForeignKey(name = "distritos_cod_departamento_fkey"))
    private Departamento departamento;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "usuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario"))
//    private Usuarios usuario;


}
