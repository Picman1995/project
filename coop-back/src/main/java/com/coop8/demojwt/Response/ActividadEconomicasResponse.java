package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.ActividadEconomicasResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadEconomicasResponse {

    private List<ActividadEconomicasResponseModel> actividadEconomicas;
	private PaginacionResponse paginacion;

}
