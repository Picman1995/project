package com.coop8.demojwt.Response;

import com.coop8.demojwt.Models.Distrito;
import com.coop8.demojwt.PayloadModels.DepartamentosResponseModel;
import com.coop8.demojwt.PayloadModels.DistritoResponseModel;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author picman
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistritoResponse implements Serializable {
    private static final long serialVersionUID = 1L;

	private List<DistritoResponseModel> distritos;
	private PaginacionResponse paginacion;

}


