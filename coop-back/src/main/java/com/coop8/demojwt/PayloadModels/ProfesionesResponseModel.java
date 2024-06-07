package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.Profesiones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesionesResponseModel {


    private Integer codProfesiones;   
    
    private String descripcion;


    public void filterPayloadToSend(Profesiones profesiones) {
        this.codProfesiones = profesiones.getCodProfesiones(); // Ajustado al nuevo nombre del método getter
        this.descripcion = profesiones.getDescripcion();        
    }

}
