/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.Controllers;

import com.coop8.demojwt.Request.PaisRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.PaisService;
import com.coop8.demojwt.Utils.Util;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author picman
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/paises")
@Slf4j
public class PaisController {

    @Autowired
    PaisService paisService;

    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody PaisRequest paisRequest) throws Exception {
        log.info("PaisController | getById");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));
        return paisService.getById(paisRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody PaisRequest paisRequest) throws Exception {
        log.info("PaisController | list");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));
        return paisService.list(paisRequest);
    }

@PostMapping("/deleteById")
public SecuredResponse deleteById(@Valid @RequestBody PaisRequest paisRequest) throws Exception {
    log.info("PaisController | deleteById");
    log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));
    
    // Agregar impresión del ID del país
    log.info("ID del país a eliminar: " + paisRequest.getCodPais());

    return paisService.deleteById(paisRequest);
}


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody PaisRequest paisRequest) {
        return paisService.save(paisRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody PaisRequest paisRequest) throws Exception {
        log.info("PaisController | newAction");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));

        // Llamar al método del servicio para crear un nuevo país
        return paisService.newAction(paisRequest);
    }
}

