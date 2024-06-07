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
public class PaisRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codPais;
    private String descripcion;

    private PaginacionRequest paginacion;

}
