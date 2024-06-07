package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.EstadosResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadosResponse {


	private List<EstadosResponseModel> estados;
	private PaginacionResponse paginacion;

}
