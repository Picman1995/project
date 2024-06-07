package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.CategoriaSocios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaSociosResponseModel {

    private Integer codCategoriaSocios;   
    
    private String descripcion;

      public void filterPayloadToSend(CategoriaSocios categoriaSocios) {
        this.codCategoriaSocios = categoriaSocios.getCodCategoriaSocios(); // Ajustado al nuevo nombre del método getter
        this.descripcion = categoriaSocios.getDescripcion();        
    }

}
