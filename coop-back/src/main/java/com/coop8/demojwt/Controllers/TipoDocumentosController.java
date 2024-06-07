package com.coop8.demojwt.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoDocumentosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoDocumentosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoDocumentos")
@Slf4j
public class TipoDocumentosController {
	String BaseUrlEndPoint;

	public TipoDocumentosController() {
		BaseUrlEndPoint = "/referenciales/tipoDocumentos";
		log.info("__BASE_end_point:	" + BaseUrlEndPoint);
	}

	@Autowired
	TipoDocumentosService tipoDocumentosService;

	
	@PostMapping("/new")
	public ResponseEntity<SecuredResponse> newAction(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) {
        try {
            SecuredResponse response = tipoDocumentosService.newAction(tipoDocumentosRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error en newAction", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosController | list");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));
        return tipoDocumentosService.list(tipoDocumentosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosController | getById");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));
        return tipoDocumentosService.getById(tipoDocumentosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosController | deleteById");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del país a eliminar: " + tipoDocumentosRequest.getCodTipoDocumentos());
        return tipoDocumentosService.deleteById(tipoDocumentosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) {
        return tipoDocumentosService.save(tipoDocumentosRequest);
    }





}