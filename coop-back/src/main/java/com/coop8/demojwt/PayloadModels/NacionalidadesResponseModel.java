package com.coop8.demojwt.PayloadModels;


import com.coop8.demojwt.Models.Nacionalidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NacionalidadesResponseModel {


	private Integer id;

	private String descripcion;

	public void filterPayloadToSend(Nacionalidades nacionalidades) {
		this.id = nacionalidades.getId();
		this.descripcion = nacionalidades.getDescripcion();
	}
}