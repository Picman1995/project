package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoVehiculosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoVehiculosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoVehiculos")
@Slf4j
public class TipoVehiculosController {

    @Autowired
    TipoVehiculosService tipoVehiculosService;

    
    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosController | getByCodTipoVehiculos");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));
        return tipoVehiculosService.getById(tipoVehiculosRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosController | list");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));
        return tipoVehiculosService.list(tipoVehiculosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosController | deleteById");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));
    
        // Agregar impresión del ID del tipo de Vehiculo
        log.info("ID del Tipo de Vehiculo a eliminar: " + tipoVehiculosRequest.getCodTipoVehiculos());

        return tipoVehiculosService.deleteById(tipoVehiculosRequest);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) {
        return tipoVehiculosService.save(tipoVehiculosRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosController | newAction");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        // Llamar al método del servicio para crear un nuevo tipo Vehiculo
        return tipoVehiculosService.newAction(tipoVehiculosRequest);
    }
}
