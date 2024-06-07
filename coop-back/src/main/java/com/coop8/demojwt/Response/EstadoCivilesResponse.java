package com.coop8.demojwt.Response;

import java.io.Serializable;
import java.util.List;

import com.coop8.demojwt.PayloadModels.EstadoCivilesResponseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCivilesResponse implements Serializable {


	private List<EstadoCivilesResponseModel> estadoCiviles;
	private PaginacionResponse paginacion;

}
