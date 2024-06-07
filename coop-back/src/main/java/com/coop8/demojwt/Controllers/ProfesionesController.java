package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.ProfesionesRequest;
import com.coop8.demojwt.Request.ProfesionesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.ProfesionesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/profesiones")
@Slf4j
public class ProfesionesController {


    String BaseUrlEndPoint;

    public ProfesionesController() {
        BaseUrlEndPoint = "/referenciales/profesiones";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }

    @Autowired
    ProfesionesService profesionesService;



    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody ProfesionesRequest profesionesRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(profesionesService.newAction(profesionesRequest), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesController | list");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));
        return profesionesService.list(profesionesRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesController | getById");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));
        return profesionesService.getById(profesionesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesController | deleteById");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));
        // Agregar impresión del ID del tipo Vinculos
        log.info("ID de profesiones a eliminar: " + profesionesRequest.getCodProfesiones());
        return profesionesService.deleteById(profesionesRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ProfesionesRequest profesionesRequest) {
        return profesionesService.save(profesionesRequest);
    }


    

}
