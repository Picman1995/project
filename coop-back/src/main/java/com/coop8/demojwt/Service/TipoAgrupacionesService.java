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
import com.coop8.demojwt.Models.TipoAgrupaciones;
import com.coop8.demojwt.PayloadModels.TipoAgrupacionesResponseModel;
import com.coop8.demojwt.Repository.TipoAgrupacionesRepository;
import com.coop8.demojwt.Request.TipoAgrupacionesRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoAgrupacionesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoAgrupacionesService {

    @Autowired
    TipoAgrupacionesRepository tipoAgrupacionesRepository;
    
    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesService | newAction");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoAgrupacionesRequest.getCodTipoAgrupaciones()) || tipoAgrupacionesRequest.getCodTipoAgrupaciones() == 0) {
            // INSERT
            TipoAgrupaciones tipoAgrupacionesInsert = new TipoAgrupaciones();
            tipoAgrupacionesInsert.setDescripcion(tipoAgrupacionesRequest.getDescripcion().toUpperCase());
            tipoAgrupacionesInsert.setFechasys(new Date());
            tipoAgrupacionesInsert.setUsuariosys(usuariosys);

            tipoAgrupacionesRepository.save(tipoAgrupacionesInsert);

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

public SecuredResponse getById(TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesService | getById");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

        TipoAgrupacionesResponse tipoAgrupacionesResponse = new TipoAgrupacionesResponse();

        Optional<TipoAgrupaciones> tipoAgrupaciones = tipoAgrupacionesRepository.findById(tipoAgrupacionesRequest.getCodTipoAgrupaciones());

        if (tipoAgrupaciones.isPresent()) {
            List<TipoAgrupaciones> tipoAgrupacionesList = new ArrayList<>();
            tipoAgrupacionesList.add(tipoAgrupaciones.get());

            List<TipoAgrupacionesResponseModel> tipoAgrupacionesResponseModels = new ArrayList<>();

            for (TipoAgrupaciones tipoAgrupacionesEntity : tipoAgrupacionesList) {
                TipoAgrupacionesResponseModel eu = new TipoAgrupacionesResponseModel();
                eu.filterPayloadToSend(tipoAgrupacionesEntity);
                tipoAgrupacionesResponseModels.add(eu);
            }

            tipoAgrupacionesResponse.setTipoAgrupaciones(tipoAgrupacionesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoAgrupacionesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesService | list");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

        List<TipoAgrupaciones> tipoAgrupacionesList = tipoAgrupacionesRepository.findAll();

        List<TipoAgrupacionesResponseModel> tipoAgrupacionesResponseModels = new ArrayList<>();
        for (TipoAgrupaciones tipoAgrupaciones : tipoAgrupacionesList) {
            TipoAgrupacionesResponseModel responseModel = new TipoAgrupacionesResponseModel();
            responseModel.filterPayloadToSend(tipoAgrupaciones);
            tipoAgrupacionesResponseModels.add(responseModel);
        }
        

        TipoAgrupacionesResponse tipoAgrupacionesResponse = new TipoAgrupacionesResponse();
        tipoAgrupacionesResponse.setTipoAgrupaciones(tipoAgrupacionesResponseModels);

        return new ResponseEntity<>(tipoAgrupacionesResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoAgrupacionesRequest tipoAgrupacionesRequest) throws Exception {
        log.info("TipoAgrupacionesService | deleteById");
        log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

        // Agregar impresión del ID del país
        log.info("ID de tipo agrupaciones a eliminar: " + tipoAgrupacionesRequest.getCodTipoAgrupaciones());

        int codTipoAgrupaciones = tipoAgrupacionesRequest.getCodTipoAgrupaciones();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoAgrupacionesRepository.deleteById(codTipoAgrupaciones);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoAgrupacionesRequest tipoAgrupacionesRequest) {
        try {
            log.info("TipoAgrupacionesController | save");
            log.info("__tipoAgrupacionesRequest: " + Util.getJsonFromObject(tipoAgrupacionesRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoAgrupacionesRequest.getCodTipoAgrupaciones() != null && tipoAgrupacionesRequest.getCodTipoAgrupaciones() != 0) {
                // UPDATE
                int codTipoAgrupaciones = tipoAgrupacionesRequest.getCodTipoAgrupaciones();

                Optional<TipoAgrupaciones> optionalTipoAgrupaciones = tipoAgrupacionesRepository.findById(codTipoAgrupaciones);
                if (optionalTipoAgrupaciones.isPresent()) {
                    TipoAgrupaciones tipoAgrupacionesUpdate = optionalTipoAgrupaciones.get();
                    tipoAgrupacionesUpdate.setDescripcion(tipoAgrupacionesRequest.getDescripcion().toUpperCase());

                    tipoAgrupacionesRepository.save(tipoAgrupacionesUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo agrupaciones con el ID proporcionado no existe");
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
            log.error("Error al guardar tipo agrupaciones: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo agrupaciones: " + e.getMessage());
        }
    }

}
