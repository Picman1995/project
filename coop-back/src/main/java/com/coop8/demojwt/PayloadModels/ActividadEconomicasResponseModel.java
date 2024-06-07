package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.ActividadEconomicas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadEconomicasResponseModel {


    private Integer codActividadEconomicas;   
    
    private String descripcion;

    public void filterPayloadToSend(ActividadEconomicas actividadEconomicas) {
        this.codActividadEconomicas= actividadEconomicas.getCodActividadEconomicas(); // Ajustado al nuevo nombre del método getter
        this.descripcion = actividadEconomicas.getDescripcion();        
    }

}
