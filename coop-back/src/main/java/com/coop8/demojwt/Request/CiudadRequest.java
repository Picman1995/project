/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.Request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author picman
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CiudadRequest implements Serializable {
    
    	private static final long serialVersionUID = -5858040595024021418L;

    private Integer id;
    private Integer codigoSet;
    private Integer codigoSicoop;
    private Integer distrito;
    private String descripcion;
    
    private PaginacionRequest paginacion;

    
}
    

