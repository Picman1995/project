package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoSolicitudes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoSolicitudesResponseModel {
    private Integer codTipoSolicitudes;   
    
    private String descripcion;


    public void filterPayloadToSend(TipoSolicitudes tipoSolicitudes) {
        this.codTipoSolicitudes = tipoSolicitudes.getCodTipoSolicitudes(); // Ajustado al nuevo nombre del método getter
        this.descripcion = tipoSolicitudes.getDescripcion();        
    }

}
