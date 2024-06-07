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
@Table(name = "conceptos")
public class Conceptos extends DescripcionFijo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_conceptos",columnDefinition = "SERIAL")
    private Long id;

}
