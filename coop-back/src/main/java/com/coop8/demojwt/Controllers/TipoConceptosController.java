package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoConceptosRequest;
import com.coop8.demojwt.Request.TipoConceptosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoConceptosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoConceptos")
@Slf4j
public class TipoConceptosController {


    String BaseUrlEndPoint;

    public TipoConceptosController() {
        BaseUrlEndPoint = "/referenciales/tipoConceptos";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }
    

     @Autowired
    TipoConceptosService tipoConceptosService;


     @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(tipoConceptosService.newAction(tipoConceptosRequest), HttpStatus.OK);
    }


     @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoConceptosController | list");
        log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));
        return tipoConceptosService.list(tipoConceptosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoConceptosController | getById");
        log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));
        return tipoConceptosService.getById(tipoConceptosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoVinculosController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(tipoConceptosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del país a eliminar: " + tipoConceptosRequest.getCodTipoConceptos());
        return tipoConceptosService.deleteById(tipoConceptosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) {
        return tipoConceptosService.save(tipoConceptosRequest);
    }





}
