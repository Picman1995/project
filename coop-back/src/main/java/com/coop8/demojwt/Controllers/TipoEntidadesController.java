package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoEntidadesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoEntidadesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author jacuna
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoEntidades")
@Slf4j
public class TipoEntidadesController {
    
    @Autowired
    TipoEntidadesService tipoEntidadesService;

    
    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesController | getByCodTipoEntidades");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));
        return tipoEntidadesService.getById(tipoEntidadesRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesController | list");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));
        return tipoEntidadesService.list(tipoEntidadesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesController | deleteById");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));
    
        // Agregar impresión del ID del tipo de entidad
        log.info("ID del Tipo de Entidad a eliminar: " + tipoEntidadesRequest.getCodTipoEntidades());

        return tipoEntidadesService.deleteById(tipoEntidadesRequest);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) {
        return tipoEntidadesService.save(tipoEntidadesRequest);
    }

    @PostMapping("/new")
    public SecuredResponse newAction(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesController | newAction");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        // Llamar al método del servicio para crear un nuevo tipo entidad
        return tipoEntidadesService.newAction(tipoEntidadesRequest);
    }

}
