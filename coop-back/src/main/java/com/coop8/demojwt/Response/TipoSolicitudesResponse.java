package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoSolicitudesResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoSolicitudesResponse {

    private List<TipoSolicitudesResponseModel> tipoSolicitudes;
	private PaginacionResponse paginacion;

}
