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
@Table(name = "departamentos", uniqueConstraints = {
    @UniqueConstraint(name = "uk_departamentos_codigo_sicoop", columnNames = "codigo_sicoop")
})
public class Departamento extends DescripcionFijo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Integer codDepartamento;

    @Column(name = "visible", nullable = false, columnDefinition = "boolean default true")
    private Boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais", foreignKey = @ForeignKey(name = "fk_departamento_pais"))
    private Pais pais;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "usuario", referencedColumnName = "name", foreignKey = @ForeignKey(name = "fk_usuario"))
//    private OxUser usuario;
    
}
