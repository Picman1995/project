package com.coop8.demojwt.PayloadModels;

import java.io.Serializable;

import com.coop8.demojwt.Models.Departamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentosResponseModel implements Serializable {

    private static final long serialVersionUID = 3481114364879410493L;

    private Integer id;  
    private String descripcion;
    private String pais;

public void filterPayloadToSend(Departamento departamento) {
    this.id = departamento.getCodDepartamento(); 
    this.descripcion = departamento.getDescripcion();  
    this.pais = departamento.getPais() != null ? departamento.getPais().getDescripcion() : null;

}
}
