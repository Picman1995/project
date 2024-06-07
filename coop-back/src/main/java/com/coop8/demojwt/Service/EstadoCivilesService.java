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
import com.coop8.demojwt.Models.EstadoCiviles;

import com.coop8.demojwt.PayloadModels.EstadoCivilesResponseModel;
import com.coop8.demojwt.Repository.EstadoCivilesRepository;
import com.coop8.demojwt.Request.EstadoCivilesRequest;

import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.EstadoCivilesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EstadoCivilesService {

    @Autowired
    EstadoCivilesRepository estadoCivilesRepository;
    
    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesService | newAction");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(estadoCivilesRequest.getCodEstadoCiviles()) || estadoCivilesRequest.getCodEstadoCiviles() == 0) {
            // INSERT
            EstadoCiviles estadoCivilesInsert = new EstadoCiviles();
            estadoCivilesInsert.setDescripcion(estadoCivilesRequest.getDescripcion().toUpperCase());
            estadoCivilesInsert.setFechasys(new Date());
            estadoCivilesInsert.setUsuariosys(usuariosys);

            estadoCivilesRepository.save(estadoCivilesInsert);

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

public SecuredResponse getById(EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesService | getById");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

        EstadoCivilesResponse estadoCivilesResponse = new EstadoCivilesResponse();

        Optional<EstadoCiviles> estadoCiviles = estadoCivilesRepository.findById(estadoCivilesRequest.getCodEstadoCiviles());

        if (estadoCiviles.isPresent()) {
            List<EstadoCiviles> estadoCivilesList = new ArrayList<>();
            estadoCivilesList.add(estadoCiviles.get());

            List<EstadoCivilesResponseModel> estadoCivilesResponseModels = new ArrayList<>();

            for (EstadoCiviles estadoCivilesEntity : estadoCivilesList) {
                EstadoCivilesResponseModel eu = new EstadoCivilesResponseModel();
                eu.filterPayloadToSend(estadoCivilesEntity);
                estadoCivilesResponseModels.add(eu);
            }

            estadoCivilesResponse.setEstadoCiviles(estadoCivilesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(estadoCivilesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesService | list");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

        List<EstadoCiviles> estadoCivilesList = estadoCivilesRepository.findAll();

        List<EstadoCivilesResponseModel> estadoCivilesResponseModels = new ArrayList<>();
        for (EstadoCiviles estadoCiviles : estadoCivilesList) {
            EstadoCivilesResponseModel responseModel = new EstadoCivilesResponseModel();
            responseModel.filterPayloadToSend(estadoCiviles);
            estadoCivilesResponseModels.add(responseModel);
        }
        

        EstadoCivilesResponse estadoCivilesResponse = new EstadoCivilesResponse();
        estadoCivilesResponse.setEstadoCiviles(estadoCivilesResponseModels);

        return new ResponseEntity<>(estadoCivilesResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(EstadoCivilesRequest estadoCivilesRequest) throws Exception {
        log.info("EstadoCivilesService | deleteById");
        log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + estadoCivilesRequest.getCodEstadoCiviles());

        int codEstadoCiviles = estadoCivilesRequest.getCodEstadoCiviles();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        estadoCivilesRepository.deleteById(codEstadoCiviles);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody EstadoCivilesRequest estadoCivilesRequest) {
        try {
            log.info("TipVinculosController | save");
            log.info("__estadoCivilesRequest: " + Util.getJsonFromObject(estadoCivilesRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (estadoCivilesRequest.getCodEstadoCiviles() != null && estadoCivilesRequest.getCodEstadoCiviles() != 0) {
                // UPDATE
                int codEstadoCiviles = estadoCivilesRequest.getCodEstadoCiviles();

                Optional<EstadoCiviles> optionalEstadoCiviles = estadoCivilesRepository.findById(codEstadoCiviles);
                if (optionalEstadoCiviles.isPresent()) {
                    EstadoCiviles estadoCivilesUpdate = optionalEstadoCiviles.get();
                    estadoCivilesUpdate.setDescripcion(estadoCivilesRequest.getDescripcion().toUpperCase());

                    estadoCivilesRepository.save(estadoCivilesUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El estado civiles con el ID proporcionado no existe");
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
            log.error("Error al guardar el estado civiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar estado civiles: " + e.getMessage());
        }
    }


}
