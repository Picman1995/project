package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.Estados;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadosResponseModel {


	private Integer codEstados;
	private String descripcion;

	public void filterPayloadToSend(Estados estados) {
		this.codEstados = estados.getCodEstados();
		this.descripcion = estados.getDescripcion();
	}
}