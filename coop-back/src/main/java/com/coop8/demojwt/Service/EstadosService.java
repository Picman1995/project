package com.coop8.demojwt.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Estados;
import com.coop8.demojwt.Models.Estados;
import com.coop8.demojwt.PayloadModels.EstadosResponseModel;
import com.coop8.demojwt.Repository.EstadosRepository;
import com.coop8.demojwt.Request.EstadosRequest;
import com.coop8.demojwt.Request.EstadosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.EstadosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EstadosService {

    @Autowired
    EstadosRepository estadosRepository;

    @Autowired
    JwtService jwtService;


    public SecuredResponse newAction(@Valid @RequestBody EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosService | newAction");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));
    
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();
    
        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();
    
        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(estadosRequest.getCodEstados()) || estadosRequest.getCodEstados() == 0) {
            // INSERT
            Estados estadoInsert = new Estados();
            estadoInsert.setDescripcion(estadosRequest.getDescripcion().toUpperCase());
            estadoInsert.setFechasys(new Date());
            estadoInsert.setUsuariosys(usuariosys);
    
            estadosRepository.save(estadoInsert);
    
            header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
            header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
            response.setHeader(header);
            response.setData(null);
    
            // Creating the token with extra claims
            String token = jwtService.getDataFromPayload(response);
            securedResponse.setData(token);
    
            log.info("__response: " + Util.getJsonFromObject(response));
            log.info("__securedResponse: " + Util.getJsonFromObject(securedResponse));
            log.info("Token generado: " + token);
        } else {
            // Se proporcionó un ID para una operación de creación
            header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
            header.setTxtResultado("Se proporcionó un ID para una operación de creación");
            response.setHeader(header);
            response.setData(null);
    
            // Creating the token with extra claims
            String token = jwtService.getDataFromPayload(response);
            securedResponse.setData(token);
    
            log.error("__response: " + Util.getJsonFromObject(response));
            log.error("__securedResponse: " + Util.getJsonFromObject(securedResponse));
            log.error("Token generado: " + token);
        }
    
        return securedResponse;
    }

public SecuredResponse getById(EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosService | getById");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));

        EstadosResponse estadosResponse = new EstadosResponse();

        Optional<Estados> estados = estadosRepository.findById(estadosRequest.getCodEstados());

        if (estados.isPresent()) {
            List<Estados> estadosList = new ArrayList<>();
            estadosList.add(estados.get());

            List<EstadosResponseModel> estadosResponseModels = new ArrayList<>();

            for (Estados estadosEntity : estadosList) {
                EstadosResponseModel eu = new EstadosResponseModel();
                eu.filterPayloadToSend(estadosEntity);
                estadosResponseModels.add(eu);
            }

            estadosResponse.setEstados(estadosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(estadosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosService | list");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));

        List<Estados> estadosList = estadosRepository.findAll();

        List<EstadosResponseModel> estadosResponseModels = new ArrayList<>();
        for (Estados estados : estadosList) {
            EstadosResponseModel responseModel = new EstadosResponseModel();
            responseModel.filterPayloadToSend(estados);
            estadosResponseModels.add(responseModel);
        }
        

        EstadosResponse estadosResponse = new EstadosResponse();
        estadosResponse.setEstados(estadosResponseModels);

        return new ResponseEntity<>(estadosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(EstadosRequest estadosRequest) throws Exception {
        log.info("EstadosService | deleteById");
        log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));

        // Agregar impresión del ID del país
        log.info("ID de estado a eliminar: " + estadosRequest.getCodEstados());

        int codEstados = estadosRequest.getCodEstados();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        estadosRepository.deleteById(codEstados);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody EstadosRequest estadosRequest) {
        try {
            log.info("EstadosController | save");
            log.info("__estadosRequest: " + Util.getJsonFromObject(estadosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (estadosRequest.getCodEstados() != null && estadosRequest.getCodEstados() != 0) {
                // UPDATE
                int codEstados = estadosRequest.getCodEstados();

                Optional<Estados> optionalEstados = estadosRepository.findById(codEstados);
                if (optionalEstados.isPresent()) {
                    Estados estadosUpdate = optionalEstados.get();
                    estadosUpdate.setDescripcion(estadosRequest.getDescripcion().toUpperCase());

                    estadosRepository.save(estadosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El estado con el ID proporcionado no existe");
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.badRequest().body(securedResponse);
                }
            } else {
                // No se proporcionó un ID válido para la actualización
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("No se proporcionó un ID válido para la actualización");
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.badRequest().body(securedResponse);
            }
        } catch (Exception e) {
            log.error("Error al guardar el estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el estado: " + e.getMessage());
        }
    }
}