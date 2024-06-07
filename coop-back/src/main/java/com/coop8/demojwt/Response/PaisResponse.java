/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.Response;

import com.coop8.demojwt.PayloadModels.PaisResponseModel;
import java.io.Serializable;
import java.util.List;
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
public class PaisResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PaisResponseModel> paises;
    private PaginacionResponse paginacion;

}
