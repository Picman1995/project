package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.CategoriaSociosResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaSociosResponse {

     private List<CategoriaSociosResponseModel> categoriaSocios;
	private PaginacionResponse paginacion;

}
