package com.coop8.demojwt.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.EstadosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.EstadosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:3000")@RestController
@RequestMapping("/referenciales/estados")
@Slf4j
public class EstadosController {
	String BaseUrlEndPoint;

	public EstadosController() {
		BaseUrlEndPoint = "/referenciales/estados";
		log.info("__BASE_end_point:	" + BaseUrlEndPoint);
	}

	@Autowired
	EstadosService estadosService;


	@PostMapping("/new")
	public ResponseEntity<?> newAction(@Valid @RequestBody EstadosRequest estadosRequest) throws Exception {
		log.info("__end_point:	" + BaseUrlEndPoint + "/newAction");
		return new ResponseEntity<>(estadosService.newAction(estadosRequest), HttpStatus.OK);
	}

	@PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosController | list");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));
        return estadosService.list(estadosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosController | getById");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));
        return estadosService.getById(estadosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody EstadosRequest estadosRequest) throws Exception {
        log.info("TipoVinculosController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(estadosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID de estados a eliminar: " + estadosRequest.getCodEstados());
        return estadosService.deleteById(estadosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EstadosRequest estadosRequest) {
        return estadosService.save(estadosRequest);
    }

	
}