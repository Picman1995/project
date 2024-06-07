package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Service.TipoVinculosService;
import com.coop8.demojwt.Utils.Util;
import com.coop8.demojwt.Request.TipoVinculosRequest;
import com.coop8.demojwt.Response.SecuredResponse;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoVinculos")
@Slf4j
public class TipoVinculosController {

    String BaseUrlEndPoint;

    public TipoVinculosController() {
        BaseUrlEndPoint = "/referenciales/tipoVinculos";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }

    @Autowired
    TipoVinculosService tipoVinculosService;

    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(tipoVinculosService.newAction(tipoVinculosRequest), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosController | list");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));
        return tipoVinculosService.list(tipoVinculosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosController | getById");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));
        return tipoVinculosService.getById(tipoVinculosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(tipoVinculosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del país a eliminar: " + tipoVinculosRequest.getCodTipoVinculos());
        return tipoVinculosService.deleteById(tipoVinculosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) {
        return tipoVinculosService.save(tipoVinculosRequest);
    }

}
