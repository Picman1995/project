/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.Distrito;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author piman
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistritoResponseModel implements Serializable {

    private static final long serialVersionUID = 3481114364879410493L;

    private Integer id;  
    private String descripcion;
    private Integer codigoSet;
    private Integer codigoSicoop;
    private String departamento;
    private Integer idDepartamento; 

    public void filterPayloadToSend(Distrito distrito) {
        this.id = distrito.getCodDistrito(); 
        this.descripcion = distrito.getDescripcion();  
        this.codigoSet = distrito.getCodigoSet();
        this.codigoSicoop = distrito.getCodigoSicoop();
        this.departamento = distrito.getDepartamento() != null ? distrito.getDepartamento().getDescripcion() : null;
        this.idDepartamento = distrito.getDepartamento() != null ? distrito.getDepartamento().getCodDepartamento() : null;
    }
}