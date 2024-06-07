package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.Entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntidadesResponseModel {

    private Integer codEntidades;
    private String descripcion;

    public void filterPayloadToSend(Entidades entidades) {
        this.codEntidades = entidades.getCodEntidades();
        this.descripcion = entidades.getDescripcion();
        // Incluir otros campos que desees enviar en el response aquí
    }

}