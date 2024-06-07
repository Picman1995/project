package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.CargoLaborales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoLaboralesResponseModel {


    private Integer codCargoLaborales;   
    
    private String descripcion;

    public void filterPayloadToSend(CargoLaborales cargoLaborales) {
        this.codCargoLaborales= cargoLaborales.getCodCargoLaborales(); // Ajustado al nuevo nombre del método getter
        this.descripcion = cargoLaborales.getDescripcion();        
    }


}
