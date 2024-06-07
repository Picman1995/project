package com.coop8.demojwt.PayloadModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptosResponseModel {


    private Long id;   
    
    private String descripcion;


    public void filterPayloadToSend(ConceptosResponseModel Conceptos) {
        this.id = Conceptos.getId(); // Ajustado al nuevo nombre del método getter
        this.descripcion = Conceptos.getDescripcion();        
    }

}
