/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.Controllers;

import com.coop8.demojwt.Request.DistritoRequest;
import com.coop8.demojwt.Service.DistritoService;
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
@RequestMapping("/referenciales/distritos")
@Slf4j
public class DistritoController {

    @Autowired
    DistritoService distritoService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
        log.info("__end_point: /referenciales/distritos/list");
        return distritoService.list(distritoRequest);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getById(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
        log.info("__end_point: /referenciales/distritos/getById");
        return new ResponseEntity<>(distritoService.getById(distritoRequest), HttpStatus.OK);
    }

    @PostMapping("/newAction")
    public ResponseEntity<?> newAction(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
        log.info("__end_point: /referenciales/distritos/newAction");
        return new ResponseEntity<>(distritoService.newAction(distritoRequest), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
        log.info("__end_point: /referenciales/distritos/save");
        return new ResponseEntity<>(distritoService.save(distritoRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
        log.info("__end_point: /referenciales/distritos/deleteById");
        return new ResponseEntity<>(distritoService.deleteById(distritoRequest), HttpStatus.OK);
    }
}
