package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoVinculos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoVinculosResponseModel {

    private Integer codTipoVinculos;
    
    private String descripcion;


    public void filterPayloadToSend(TipoVinculos tipoVinculos) {
        this.codTipoVinculos = tipoVinculos.getCodTipoVinculos(); // Ajustado al nuevo nombre del método getter
        this.descripcion = tipoVinculos.getDescripcion();        
    }



}
