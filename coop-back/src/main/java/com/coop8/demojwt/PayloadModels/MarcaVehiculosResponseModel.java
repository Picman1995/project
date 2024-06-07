package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.MarcaVehiculos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcaVehiculosResponseModel {


    private Integer codMarcaVehiculos;   
    
    private String descripcion;

    public void filterPayloadToSend(MarcaVehiculos marcaVehiculos) {
        this.codMarcaVehiculos = marcaVehiculos.getCodMarcaVehiculos(); // Ajustado al nuevo nombre del método getter
        this.descripcion = marcaVehiculos.getDescripcion();        
    }


}
