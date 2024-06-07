package com.coop8.demojwt.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPersonasRequest {

	private Integer codTipoPersonas;
	private String descripcion;
	
	private PaginacionRequest paginacion;

}
