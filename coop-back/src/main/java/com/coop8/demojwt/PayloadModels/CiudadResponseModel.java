package com.coop8.demojwt.PayloadModels;

import java.io.Serializable;

import com.coop8.demojwt.Models.Ciudad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CiudadResponseModel implements Serializable {

    private static final long serialVersionUID = 3481114364879410493L;

    private Integer id;  
    private String descripcion;
    private Integer codigoSet;
    private Integer codigoSicoop;
    private String distrito;
    private Integer idDistrito;

    public void filterPayloadToSend(Ciudad ciudades) {
        this.id = ciudades.getCodCiudad(); 
        this.descripcion = ciudades.getDescripcion();  
        this.codigoSet = ciudades.getCodigoSet();
        this.codigoSicoop = ciudades.getCodigoSicoop();
        this.distrito = ciudades.getDistrito() != null ? ciudades.getDistrito().getDescripcion() : null;
        this.idDistrito = ciudades.getDistrito() != null ? ciudades.getDistrito().getCodDistrito() : null;

        
    }
}
