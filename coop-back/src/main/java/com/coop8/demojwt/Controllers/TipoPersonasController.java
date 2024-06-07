package com.coop8.demojwt.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoPersonasRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoPersonasService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/referenciales/tipoPersonas")
@Slf4j
public class TipoPersonasController {
	String BaseUrlEndPoint;

	public TipoPersonasController() {
		BaseUrlEndPoint = "/referenciales/tipoPersonas";
		log.info("__BASE_end_point:	" + BaseUrlEndPoint);
	}

	@Autowired
	TipoPersonasService tipoPersonasService;

	

	@PostMapping("/new")
	public ResponseEntity<?> newAction(@Valid @RequestBody TipoPersonasRequest tipoPersonaRequest) throws Exception {
		log.info("__end_point:	" + BaseUrlEndPoint + "/newAction");
		return new ResponseEntity<>(tipoPersonasService.newAction(tipoPersonaRequest), HttpStatus.OK);
	}

	@PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasController | list");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));
        return tipoPersonasService.list(tipoPersonasRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasController | getById");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));
        return tipoPersonasService.getById(tipoPersonasRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasController | deleteById");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del tipo personas a eliminar: " + tipoPersonasRequest.getCodTipoPersonas());
        return tipoPersonasService.deleteById(tipoPersonasRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoPersonasRequest tipoPersonasRequest) {
        return tipoPersonasService.save(tipoPersonasRequest);
    }

	
}