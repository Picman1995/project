package com.coop8.demojwt.PayloadModels;


import com.coop8.demojwt.Models.ModeloVehiculos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModeloVehiculosResponseModel{


    private Integer codModeloVehiculos;
    private String descripcion;

    public void filterPayloadToSend(ModeloVehiculos modeloVehiculos) {
        this.codModeloVehiculos = modeloVehiculos.getCodModeloVehiculos();
        this.descripcion = modeloVehiculos.getDescripcion();
        // Incluir otros campos que desees enviar en el response aquí
    }

}