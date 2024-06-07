package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoContactos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoContactosResponseModel {

    private Integer codTipoContactos;   
    
    private String descripcion;

      public void filterPayloadToSend(TipoContactos tipoContactos) {
        this.codTipoContactos= tipoContactos.getCodTipoContactos(); // Ajustado al nuevo nombre del método getter
        this.descripcion = tipoContactos.getDescripcion();        
    }

}
