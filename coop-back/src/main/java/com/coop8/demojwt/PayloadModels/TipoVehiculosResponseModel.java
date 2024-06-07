package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoVehiculos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoVehiculosResponseModel{

    private Integer codTipoVehiculos;
    private String descripcion;

    public void filterPayloadToSend(TipoVehiculos tipoVehiculos) {
        this.codTipoVehiculos = tipoVehiculos.getCodTipoVehiculos();
        this.descripcion = tipoVehiculos.getDescripcion();
        // Incluir otros campos que desees enviar en el response aquí
    }

}
