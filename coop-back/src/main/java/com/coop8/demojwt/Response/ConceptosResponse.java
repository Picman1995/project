package com.coop8.demojwt.Response;

import java.util.List;

import com.coop8.demojwt.PayloadModels.ConceptosResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConceptosResponse {
    private List<ConceptosResponseModel> conceptos;
	private PaginacionResponse paginacion;

}
