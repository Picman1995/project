package com.coop8.demojwt.PayloadModels;

import java.io.Serializable;

import com.coop8.demojwt.Models.EstadoCiviles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCivilesResponseModel implements Serializable {

	private static final long serialVersionUID = 3481114364879410493L;

	private Integer codEstadoCiviles;
	private String descripcion;

	public void filterPayloadToSend(EstadoCiviles estadoCiviles) {
		this.codEstadoCiviles = estadoCiviles.getCodEstadoCiviles();
		this.descripcion = estadoCiviles.getDescripcion();
	}
}