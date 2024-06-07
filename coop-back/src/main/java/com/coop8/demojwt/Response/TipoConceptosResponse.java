package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoConceptosResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoConceptosResponse {


    private List<TipoConceptosResponseModel> tipoConceptos;
	private PaginacionResponse paginacion;


}
