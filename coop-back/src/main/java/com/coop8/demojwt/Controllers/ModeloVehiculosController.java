package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.ModeloVehiculosRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.ModeloVehiculosService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/modeloVehiculos")
@Slf4j
public class ModeloVehiculosController {

    @Autowired
    ModeloVehiculosService modeloVehiculosService;

    
    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosController | getByCodTipoVehiculos");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));
        return modeloVehiculosService.getById(modeloVehiculosRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosController | list");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));
        return modeloVehiculosService.list(modeloVehiculosRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosController | deleteById");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));
    
        // Agregar impresión del ID del modelo de Vehiculo
        log.info("ID del modelo de Vehiculo a eliminar: " + modeloVehiculosRequest.getCodModeloVehiculos());

        return modeloVehiculosService.deleteById(modeloVehiculosRequest);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) {
        return modeloVehiculosService.save(modeloVehiculosRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosController | newAction");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        // Llamar al método del servicio para crear un nuevo modelo de Vehiculo
        return modeloVehiculosService.newAction(modeloVehiculosRequest);
    }

    
}
