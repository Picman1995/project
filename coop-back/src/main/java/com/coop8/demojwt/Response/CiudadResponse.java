package com.coop8.demojwt.Response;

import com.coop8.demojwt.PayloadModels.CiudadResponseModel;
import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CiudadResponse implements Serializable {

	private static final long serialVersionUID = 8843219508588806231L;

	private List<CiudadResponseModel> ciudades;
	private PaginacionResponse paginacion;

}
