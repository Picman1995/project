package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoConceptos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoConceptosResponseModel {


    private Integer codTipoConceptos;   
    
    private String descripcion;

    public void filterPayloadToSend(TipoConceptos tipoConceptos) {
        this.codTipoConceptos = tipoConceptos.getCodTipoConceptos(); // Ajustado al nuevo nombre del método getter
        this.descripcion = tipoConceptos.getDescripcion();        
    }


}
