package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.TipoSolicitudesRequest;
import com.coop8.demojwt.Request.TipoSolicitudesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.TipoSolicitudesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/tipoSolicitudes")
@Slf4j
public class TipoSolicitudesController {


    String BaseUrlEndPoint;

    public TipoSolicitudesController() {
        BaseUrlEndPoint = "/referenciales/tipoSolicitudes";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }

    @Autowired
    TipoSolicitudesService tipoSolicitudesService;



    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(tipoSolicitudesService.newAction(tipoSolicitudesRequest), HttpStatus.OK);
    }


    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesController | list");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));
        return tipoSolicitudesService.list(tipoSolicitudesRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesController | getById");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));
        return tipoSolicitudesService.getById(tipoSolicitudesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesController | deleteById");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID del país a eliminar: " + tipoSolicitudesRequest.getCodTipoSolicitudes());
        return tipoSolicitudesService.deleteById(tipoSolicitudesRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) {
        return tipoSolicitudesService.save(tipoSolicitudesRequest);
    }



}
