package com.coop8.demojwt.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.EstadoCivilesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.EstadoCivilesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/referenciales/estadoCiviles")
@Slf4j
public class EstadoCivilesController {
	String BaseUrlEndPoint;

	public EstadoCivilesController() {
		BaseUrlEndPoint = "/referenciales/estadoCiviles";
		log.info("__BASE_end_point:	" + BaseUrlEndPoint);
	}

	@Autowired
    EstadoCivilesService estadoCivilesService;

    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(estadoCivilesService.newAction(estadoCivilesRequest), HttpStatus.OK);
    }


    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesControlleres | list");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));
        return estadoCivilesService.list(estadoCivilesRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesControlleres | getById");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));
        return estadoCivilesService.getById(estadoCivilesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesControlleres | deleteById");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del estado civiles a eliminar: " + estadoCivilesRequest.getCodEstadoCiviles());
        return estadoCivilesService.deleteById(estadoCivilesRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) {
        return estadoCivilesService.save(estadoCivilesRequest);
    }

}