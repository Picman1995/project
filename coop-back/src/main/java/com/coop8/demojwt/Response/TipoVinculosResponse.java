package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoVinculosResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoVinculosResponse {

    private List<TipoVinculosResponseModel> tipoVinculos;
	private PaginacionResponse paginacion;

}
