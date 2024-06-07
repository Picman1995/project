package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoContactosRequest;
import com.coop8.demojwt.Request.TipoContactosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoContactosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoContactos")
@Slf4j
public class TipoContactosController {


    String BaseUrlEndPoint;


    public TipoContactosController() {
        BaseUrlEndPoint = "/referenciales/tipoContactos";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }
    

     @Autowired
    TipoContactosService tipoContactosService;



    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(tipoContactosService.newAction(tipoContactosRequest), HttpStatus.OK);
    }
    
    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosController | list");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));
        return tipoContactosService.list(tipoContactosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosController | getById");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));
        return tipoContactosService.getById(tipoContactosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(tipoContactosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del contactos a eliminar: " + tipoContactosRequest.getCodTipoContactos());
        return tipoContactosService.deleteById(tipoContactosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) {
        return tipoContactosService.save(tipoContactosRequest);
    }

}
