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
@Table(name = "cargo_laborales")
public class CargoLaborales extends DescripcionFijo{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cargo_laborales",columnDefinition = "SERIAL")
    private Integer codCargoLaborales;

    
}
