package com.coop8.demojwt.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.NacionalidadesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.NacionalidadesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/nacionalidades")
@Slf4j
public class NacionalidadesController {
    
     String BaseUrlEndPoint;
    
    @Autowired
    NacionalidadesService nacionalidadesService;
    
    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("__end_point: /referenciales/nacionalidades/list");
        return nacionalidadesService.list(nacionalidadesRequest);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getById(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("__end_point: /referenciales/nacionalidades/getById");
        return new ResponseEntity<>(nacionalidadesService.getById(nacionalidadesRequest), HttpStatus.OK);
    }

    @PostMapping("/newAction")
    public ResponseEntity<?> newAction(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("__end_point: /referenciales/nacionalidades/newAction");
        return new ResponseEntity<>(nacionalidadesService.newAction(nacionalidadesRequest), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("__end_point: /referenciales/nacionalidades/save");
        return new ResponseEntity<>(nacionalidadesService.save(nacionalidadesRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("__end_point: /referenciales/nacionalidades/deleteById");
        return new ResponseEntity<>(nacionalidadesService.deleteById(nacionalidadesRequest), HttpStatus.OK);
    }
}