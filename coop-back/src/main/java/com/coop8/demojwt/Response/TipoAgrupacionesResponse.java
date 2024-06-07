package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoAgrupacionesResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoAgrupacionesResponse {


    private List<TipoAgrupacionesResponseModel> tipoAgrupaciones;
    private PaginacionResponse paginacion;
}
