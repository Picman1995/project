package com.coop8.demojwt.Controllers;

import com.coop8.demojwt.PayloadModels.PersonasResponseModel;
import com.coop8.demojwt.Request.PersonasRequest;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Service.PersonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/personas")
@Slf4j
public class PersonasController {

    @Autowired
    PersonasService personasService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@Valid @RequestBody PersonasRequest personasRequest) throws Exception {
        log.info("__end_point: /personas/list");
        return personasService.list(personasRequest);
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getById(@Valid @RequestBody PersonasRequest personasRequest) {
        log.info("__end_point: /personas/getById");
        try {
            // Llama a getById en el servicio y retorna el ResponseEntity directamente
            ResponseEntity<?> response = personasService.getById(personasRequest);
            return response;
        } catch (Exception e) {
            // Manejo de excepciones y retorno de un error apropiado
            log.error("Error en /personas/getById", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/newAction")
    public ResponseEntity<?> newAction(@Valid @RequestBody PersonasRequest personasRequest) throws Exception {
        log.info("__end_point: /personas/newAction");
        return new ResponseEntity<>(personasService.newAction(personasRequest), HttpStatus.OK);
    }


@PostMapping("/save")
public ResponseEntity<?> save(@Valid @RequestBody PersonasRequest personasRequest) {
    try {
        // Enviar la solicitud al servicio sin autenticación
        SecuredResponse securedResponse = personasService.save(personasRequest);
        return new ResponseEntity<>(securedResponse, HttpStatus.OK);
    } catch (Exception e) {
        // Manejo de excepciones
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@Valid @RequestBody PersonasRequest personasRequest) throws Exception {
        log.info("__end_point: /personas/deleteById");
        SecuredResponse response = personasService.deleteById(personasRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
