package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.EntidadesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.EntidadesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/entidades")
@Slf4j
public class EntidadesController {

    @Autowired
    EntidadesService entidadesService;

    
    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesController | getByCodTipoVehiculos");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));
        return entidadesService.getById(entidadesRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesController | list");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));
        return entidadesService.list(entidadesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesController | deleteById");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));
    
        // Agregar impresión del ID de la entidad
        log.info("ID de la entidad a eliminar: " + entidadesRequest.getCodEntidades());

        return entidadesService.deleteById(entidadesRequest);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EntidadesRequest entidadesRequest) {
        return entidadesService.save(entidadesRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesController | newAction");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        // Llamar al método del servicio para crear una nueva entidad
        return entidadesService.newAction(entidadesRequest);
    }

}
