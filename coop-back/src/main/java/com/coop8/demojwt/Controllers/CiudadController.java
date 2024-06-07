package com.coop8.demojwt.Controllers;

import com.coop8.demojwt.Request.CiudadRequest;
import com.coop8.demojwt.Service.CiudadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/referenciales/ciudades")
@Slf4j
public class CiudadController {

    @Autowired
    CiudadService ciudadService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
        log.info("__end_point: /referenciales/ciudades/list");
        return ciudadService.list(ciudadRequest);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getById(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
        log.info("__end_point: /referenciales/ciudades/getById");
        return new ResponseEntity<>(ciudadService.getById(ciudadRequest), HttpStatus.OK);
    }

    @PostMapping("/newAction")
    public ResponseEntity<?> newAction(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
        log.info("__end_point: /referenciales/ciudades/newAction");
        return new ResponseEntity<>(ciudadService.newAction(ciudadRequest), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
        log.info("__end_point: /referenciales/ciudades/save");
        return new ResponseEntity<>(ciudadService.save(ciudadRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
        log.info("__end_point: /referenciales/ciudades/deleteById");
        return new ResponseEntity<>(ciudadService.deleteById(ciudadRequest), HttpStatus.OK);
    }
}
