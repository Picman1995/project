package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.TipoContactosResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoContactosResponse {


    private List<TipoContactosResponseModel> tipoContactos;
	private PaginacionResponse paginacion;

}
