package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.MarcaVehiculosResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcaVehiculosResponse {


    private List<MarcaVehiculosResponseModel> marcaVehiculos;
	private PaginacionResponse paginacion;



}
