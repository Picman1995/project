package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoAgrupaciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoAgrupacionesResponseModel {


    private Integer codTipoAgrupaciones;
    private String descripcion;

    public void filterPayloadToSend(TipoAgrupaciones tipoAgrupaciones) {
        this.codTipoAgrupaciones = tipoAgrupaciones.getCodTipoAgrupaciones();
        this.descripcion = tipoAgrupaciones.getDescripcion();
        // Incluir otros campos que desees enviar en el response aquí
    }

}