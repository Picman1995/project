package com.coop8.demojwt.Controllers;

import com.coop8.demojwt.Request.EntidadesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.SucursalesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.EntidadesService;
import com.coop8.demojwt.Service.SucursalesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/sucursales")
@Slf4j
public class SucursalesController {

     @Autowired
    SucursalesService sucursalesService;

    
    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesController | getById");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));
        return sucursalesService.getById(sucursalesRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesController | list");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));
        return sucursalesService.list(sucursalesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesController | deleteById");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));
    
        // Agregar impresión del ID de la entidad
        log.info("ID de la sucursal a eliminar: " + sucursalesRequest.getCodSucursales());

        return sucursalesService.deleteById(sucursalesRequest);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody SucursalesRequest sucursalesRequest) {
        return sucursalesService.save(sucursalesRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesController | newAction");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        // Llamar al método del servicio para crear una nueva entidad
        return sucursalesService.newAction(sucursalesRequest);
    }

}
