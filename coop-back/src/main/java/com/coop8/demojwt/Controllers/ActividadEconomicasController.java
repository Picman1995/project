package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.ActividadEconomicasRequest;

import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.ActividadEconomicasService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/actividadEconomicas")
@Slf4j
public class ActividadEconomicasController {


    String BaseUrlEndPoint;


    public ActividadEconomicasController() {
        BaseUrlEndPoint = "/referenciales/actividadadEconomicas";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }
    

     @Autowired
    ActividadEconomicasService actividadEconomicasService;

    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(actividadEconomicasService.newAction(actividadEconomicasRequest), HttpStatus.OK);
    }

     @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasController | list");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));
        return actividadEconomicasService.list(actividadEconomicasRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasController | getById");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));
        return actividadEconomicasService.getById(actividadEconomicasRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasController | deleteById");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID de actividad economicas a eliminar: " + actividadEconomicasRequest.getCodActividadEconomicas());
        return actividadEconomicasService.deleteById(actividadEconomicasRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) {
        return actividadEconomicasService.save(actividadEconomicasRequest);
    }

}
