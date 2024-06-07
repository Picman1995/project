package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.TipoPersonas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPersonasResponseModel {

	private Integer codTipoPersonas;
	private String descripcion;

	public void filterPayloadToSend(TipoPersonas tipoPersonas) {
		this.codTipoPersonas = tipoPersonas.getCodTipoPersonas();
		this.descripcion = tipoPersonas.getDescripcion();
	}
}