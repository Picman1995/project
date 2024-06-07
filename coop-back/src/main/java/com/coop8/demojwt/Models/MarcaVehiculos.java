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
@Table(name = "marca_vehiculos")
public class MarcaVehiculos extends DescripcionFijo{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_marca_vehiculos",columnDefinition = "SERIAL")
    private Integer codMarcaVehiculos;




}
