package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoPersonasResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPersonasResponse {

	private List<TipoPersonasResponseModel> tipoPersonas;
	private PaginacionResponse paginacion;

}
