package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoEntidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEntidadesResponseModel  {
    
    private Integer codTipoEntidades;
    private String descripcion;

    public void filterPayloadToSend(TipoEntidades tipoEntidades) {
        this.codTipoEntidades = tipoEntidades.getCodTipoEntidades();
        this.descripcion = tipoEntidades.getDescripcion();
        // Incluir otros campos que desees enviar en el response aquí
    }

}