package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.CategoriaSociosRequest;
import com.coop8.demojwt.Request.CategoriaSociosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.CategoriaSociosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/categoriaSocios")
@Slf4j
public class CategoriaSociosController {


     String BaseUrlEndPoint;

    public CategoriaSociosController() {
        BaseUrlEndPoint = "/referenciales/categoriaSocios";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }

    @Autowired
    CategoriaSociosService categoriaSociosService;



    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(categoriaSociosService.newAction(categoriaSociosRequest), HttpStatus.OK);
    }

     @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSocioController | list");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));
        return categoriaSociosService.list(categoriaSociosRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSocioController | getById");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));
        return categoriaSociosService.getById(categoriaSociosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSocioController | deleteById");
        log.info("__paisRequest: " + Util.getJsonFromObject(categoriaSociosRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID categoria socios a eliminar: " + categoriaSociosRequest.getCodCategoriaSocios());
        return categoriaSociosService.deleteById(categoriaSociosRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) {
        return categoriaSociosService.save(categoriaSociosRequest);
    }

}
