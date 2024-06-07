package com.coop8.demojwt.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "actividad_economicas")
public class ActividadEconomicas extends DescripcionFijo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_actividad_economicas",columnDefinition = "SERIAL")
    private Integer codActividadEconomicas;

}
