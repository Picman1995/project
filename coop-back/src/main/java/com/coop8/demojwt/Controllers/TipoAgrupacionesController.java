package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoAgrupacionesRequest;
import com.coop8.demojwt.Request.TipoAgrupacionesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoAgrupacionesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoAgrupaciones")
@Slf4j
public class TipoAgrupacionesController {

    String BaseUrlEndPoint;

    public TipoAgrupacionesController(){
        BaseUrlEndPoint = "/referenciales/tipoAgrupaciones";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }

    @Autowired
    TipoAgrupacionesService tipoAgrupacionesService;

    

    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(tipoAgrupacionesService.newAction(tipoAgrupacionesRequest), HttpStatus.OK);
    }


@PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesController | list");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));
        return tipoAgrupacionesService.list(tipoAgrupacionesRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesController | getById");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));
        return tipoAgrupacionesService.getById(tipoAgrupacionesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del país a eliminar: " + tipoAgrupacionesRequest.getCodTipoAgrupaciones());
        return tipoAgrupacionesService.deleteById(tipoAgrupacionesRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) {
        return tipoAgrupacionesService.save(tipoAgrupacionesRequest);
    }
    
}
