package com.coop8.demojwt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop8.demojwt.Request.CargoLaboralesRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.CargoLaboralesService;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/referenciales/cargoLaborales")
@Slf4j
public class CargoLaboralesController {


    String BaseUrlEndPoint;


    public CargoLaboralesController() {
        BaseUrlEndPoint = "/referenciales/cargoLaborales";
        log.info("__BASE_end_point:    " + BaseUrlEndPoint);
    }
    

     @Autowired
    CargoLaboralesService cargoLaboralesService;


    @PostMapping("/new")
    public ResponseEntity<?> newAction(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("__end_point:    " + BaseUrlEndPoint + "/newAction");
        return new ResponseEntity<>(cargoLaboralesService.newAction(cargoLaboralesRequest), HttpStatus.OK);
    }


     @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesController | list");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));
        return cargoLaboralesService.list(cargoLaboralesRequest);
    }


    @PostMapping("/getById")
    public SecuredResponse getById(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesController | getById");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));
        return cargoLaboralesService.getById(cargoLaboralesRequest);
    }

    @PostMapping("/deleteById")
    public SecuredResponse deleteById(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesController | deleteById");
        log.info("_cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));
        // Agregar impresión del ID cargo laborales
        log.info("ID del país a eliminar: " + cargoLaboralesRequest.getCodCargoLaborales());
        return cargoLaboralesService.deleteById(cargoLaboralesRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) {
        return cargoLaboralesService.save(cargoLaboralesRequest);
    }


}
