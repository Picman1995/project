package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.DepartamentosRequest;
import com.coop8.demojwt.Service.DepartamentosService;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/departamentos")
@Slf4j
public class DepartamentosController {

    String BaseUrlEndPoint;

    @Autowired
    DepartamentosService departamentosService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point: /referenciales/departamentos/list");
        return departamentosService.list(departamentosRequest);
    }
   

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/edit");
        return new ResponseEntity<>(departamentosService.getById(departamentosRequest), HttpStatus.OK);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getById(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/getById");
        return new ResponseEntity<>(departamentosService.getById(departamentosRequest), HttpStatus.OK);
    }

    @PostMapping("/newAction")
    public ResponseEntity<?> newAction(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(departamentosService.newAction(departamentosRequest), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/save");
        return new ResponseEntity<>(departamentosService.save(departamentosRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/deleteById");
        return new ResponseEntity<>(departamentosService.deleteById(departamentosRequest), HttpStatus.OK);
    }
}
