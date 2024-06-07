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
import com.coop8.demojwt.Models.ActividadEconomicas;

import com.coop8.demojwt.PayloadModels.ActividadEconomicasResponseModel;
import com.coop8.demojwt.Repository.ActividadEconomicasRepository;
import com.coop8.demojwt.Request.ActividadEconomicasRequest;

import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.ActividadEconomicasResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActividadEconomicasService {

     @Autowired
    ActividadEconomicasRepository actividadEconomicasRepository;

    @Autowired
    JwtService jwtService;


     public SecuredResponse newAction(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasService | newAction");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(actividadEconomicasRequest.getCodActividadEconomicas()) || actividadEconomicasRequest.getCodActividadEconomicas() == 0) {
            // INSERT
            ActividadEconomicas actividadEconomicasInsert = new ActividadEconomicas();
            actividadEconomicasInsert.setDescripcion(actividadEconomicasRequest.getDescripcion().toUpperCase());
            actividadEconomicasInsert.setFechasys(new Date());
            actividadEconomicasInsert.setUsuariosys(usuariosys);

            actividadEconomicasRepository.save(actividadEconomicasInsert);

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

public SecuredResponse getById(ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasService | getById");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

        ActividadEconomicasResponse actividadEconomicasResponse = new ActividadEconomicasResponse();

        Optional<ActividadEconomicas> actividadEconomicas = actividadEconomicasRepository.findById(actividadEconomicasRequest.getCodActividadEconomicas());

        if (actividadEconomicas.isPresent()) {
            List<ActividadEconomicas> actividadEconomicasList = new ArrayList<>();
            actividadEconomicasList.add(actividadEconomicas.get());

            List<ActividadEconomicasResponseModel> actividadEconomicasResponseModels = new ArrayList<>();

            for (ActividadEconomicas actividadEconomicasEntity : actividadEconomicasList) {
                ActividadEconomicasResponseModel eu = new ActividadEconomicasResponseModel();
                eu.filterPayloadToSend(actividadEconomicasEntity);
                actividadEconomicasResponseModels.add(eu);
            }

            actividadEconomicasResponse.setActividadEconomicas(actividadEconomicasResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(actividadEconomicasResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasService | list");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

        List<ActividadEconomicas> actividadEconomicasList = actividadEconomicasRepository.findAll();

        List<ActividadEconomicasResponseModel> actividadEconomicasResponseModels = new ArrayList<>();
        for (ActividadEconomicas actividadEconomicas : actividadEconomicasList) {
            ActividadEconomicasResponseModel responseModel = new ActividadEconomicasResponseModel();
            responseModel.filterPayloadToSend(actividadEconomicas);
            actividadEconomicasResponseModels.add(responseModel);
        }
        

        ActividadEconomicasResponse actividadEconomicasResponse = new ActividadEconomicasResponse();
        actividadEconomicasResponse.setActividadEconomicas(actividadEconomicasResponseModels);

        return new ResponseEntity<>(actividadEconomicasResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(ActividadEconomicasRequest actividadEconomicasRequest) throws Exception {
        log.info("ActividadEconomicasService | deleteById");
        log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

        // Agregar impresión del ID del país
        log.info("ID de actividad economicas a eliminar: " + actividadEconomicasRequest.getCodActividadEconomicas());

        int codActividadEconomicas = actividadEconomicasRequest.getCodActividadEconomicas();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        actividadEconomicasRepository.deleteById(codActividadEconomicas);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody ActividadEconomicasRequest actividadEconomicasRequest) {
        try {
            log.info("ActividadEconomicasController | save");
            log.info("__actividadEconomicasRequest: " + Util.getJsonFromObject(actividadEconomicasRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (actividadEconomicasRequest.getCodActividadEconomicas() != null && actividadEconomicasRequest.getCodActividadEconomicas() != 0) {
                // UPDATE
                int codActividadEconomicas = actividadEconomicasRequest.getCodActividadEconomicas();

                Optional<ActividadEconomicas> optionalActividadEconomicas = actividadEconomicasRepository.findById(codActividadEconomicas);
                if (optionalActividadEconomicas.isPresent()) {
                    ActividadEconomicas actividadEconomicasUpdate = optionalActividadEconomicas.get();
                    actividadEconomicasUpdate.setDescripcion(actividadEconomicasRequest.getDescripcion().toUpperCase());

                    actividadEconomicasRepository.save(actividadEconomicasUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("Actividad Economicas con el ID proporcionado no existe");
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
            log.error("Error al guardar actividad economicas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el actividad economicas: " + e.getMessage());
        }
    }




}
