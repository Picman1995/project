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
import com.coop8.demojwt.Models.TipoPersonas;

import com.coop8.demojwt.PayloadModels.TipoPersonasResponseModel;
import com.coop8.demojwt.Repository.TipoPersonasRepository;
import com.coop8.demojwt.Request.TipoPersonasRequest;

import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoPersonasResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoPersonasService {

    @Autowired
    TipoPersonasRepository tipoPersonasRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoPersonasRequest tipoPersonaRequest) throws Exception {
        log.info("TipoPersonasService | newAction");
        log.info("tipoPersonaRequest: " + Util.getJsonFromObject(tipoPersonaRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoPersonaRequest.getCodTipoPersonas()) || tipoPersonaRequest.getCodTipoPersonas() == 0) {
            // INSERT
            TipoPersonas tipoPersonaInsert = new TipoPersonas();
            tipoPersonaInsert.setDescripcion(tipoPersonaRequest.getDescripcion().toUpperCase());
            tipoPersonaInsert.setFechasys(new Date());
            tipoPersonaInsert.setUsuariosys(usuariosys);

            tipoPersonasRepository.save(tipoPersonaInsert);

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

    public SecuredResponse getById(TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasService | getById");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));

        TipoPersonasResponse tipoPersonasResponse = new TipoPersonasResponse();

        Optional<TipoPersonas> tipoPersonas = tipoPersonasRepository.findById(tipoPersonasRequest.getCodTipoPersonas());

        if (tipoPersonas.isPresent()) {
            List<TipoPersonas> tipoPersonasList = new ArrayList<>();
            tipoPersonasList.add(tipoPersonas.get());

            List<TipoPersonasResponseModel> tipoPersonasResponseModels = new ArrayList<>();

            for (TipoPersonas tipoPersonasEntity : tipoPersonasList) {
                TipoPersonasResponseModel eu = new TipoPersonasResponseModel();
                eu.filterPayloadToSend(tipoPersonasEntity);
                tipoPersonasResponseModels.add(eu);
            }

            tipoPersonasResponse.setTipoPersonas(tipoPersonasResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoPersonasResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasService | list");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));

        List<TipoPersonas> tipoPersonasList = tipoPersonasRepository.findAll();

        List<TipoPersonasResponseModel> tipoPersonasResponseModels = new ArrayList<>();
        for (TipoPersonas tipoPersonas : tipoPersonasList) {
            TipoPersonasResponseModel responseModel = new TipoPersonasResponseModel();
            responseModel.filterPayloadToSend(tipoPersonas);
            tipoPersonasResponseModels.add(responseModel);
        }

        TipoPersonasResponse tipoPersonasResponse = new TipoPersonasResponse();
        tipoPersonasResponse.setTipoPersonas(tipoPersonasResponseModels);

        return new ResponseEntity<>(tipoPersonasResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoPersonasRequest tipoPersonasRequest) throws Exception {
        log.info("TipoPersonasService | deleteById");
        log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + tipoPersonasRequest.getCodTipoPersonas());

        int codTipoPersonas = tipoPersonasRequest.getCodTipoPersonas();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoPersonasRepository.deleteById(codTipoPersonas);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoPersonasRequest tipoPersonasRequest) {
        try {
            log.info("TipVinculosController | save");
            log.info("__tipoPersonasRequest: " + Util.getJsonFromObject(tipoPersonasRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoPersonasRequest.getCodTipoPersonas() != null && tipoPersonasRequest.getCodTipoPersonas() != 0) {
                // UPDATE
                int codTipoPersonas = tipoPersonasRequest.getCodTipoPersonas();

                Optional<TipoPersonas> optionalTipoPersonas = tipoPersonasRepository.findById(codTipoPersonas);
                if (optionalTipoPersonas.isPresent()) {
                    TipoPersonas tipoPersonasUpdate = optionalTipoPersonas.get();
                    tipoPersonasUpdate.setDescripcion(tipoPersonasRequest.getDescripcion().toUpperCase());

                    tipoPersonasRepository.save(tipoPersonasUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo personas con el ID proporcionado no existe");
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
            log.error("Error al guardar el tipo personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo personas: " + e.getMessage());
        }
    }

}
