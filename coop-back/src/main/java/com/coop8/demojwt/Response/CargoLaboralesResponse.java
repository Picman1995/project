package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.CargoLaboralesResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoLaboralesResponse {
    private List<CargoLaboralesResponseModel> cargoLaborales;
	private PaginacionResponse paginacion;

}
