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
import com.coop8.demojwt.Models.TipoConceptos;
import com.coop8.demojwt.PayloadModels.TipoConceptosResponseModel;
import com.coop8.demojwt.Repository.TipoConceptosRepository;
import com.coop8.demojwt.Request.TipoConceptosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoConceptosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoConceptosService {

    @Autowired
    TipoConceptosRepository tipoConceptosRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoConceptosRequest tipoConceptosResquest) throws Exception {
        log.info("TipoConceptosService | newAction");
        log.info("__tipoConceptosResquest: " + Util.getJsonFromObject(tipoConceptosResquest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoConceptosResquest: " + Util.getJsonFromObject(tipoConceptosResquest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoConceptosResquest.getCodTipoConceptos())
                || tipoConceptosResquest.getCodTipoConceptos() == 0) {
            // INSERT
            TipoConceptos tipoConceptosInsert = new TipoConceptos();
            tipoConceptosInsert.setDescripcion(tipoConceptosResquest.getDescripcion().toUpperCase());
            tipoConceptosInsert.setFechasys(new Date());
            tipoConceptosInsert.setUsuariosys(usuariosys);

            tipoConceptosRepository.save(tipoConceptosInsert);

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

    public SecuredResponse getById(TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoConceptosService | getById");
        log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));

        TipoConceptosResponse tipoConceptosResponse = new TipoConceptosResponse();

        Optional<TipoConceptos> tipoConceptos = tipoConceptosRepository.findById(tipoConceptosRequest.getCodTipoConceptos());

        if (tipoConceptos.isPresent()) {
            List<TipoConceptos> tipoConceptosList = new ArrayList<>();
            tipoConceptosList.add(tipoConceptos.get());

            List<TipoConceptosResponseModel> tipoConceptosResponseModels = new ArrayList<>();

            for (TipoConceptos tipoConceptosEntity : tipoConceptosList) {
                TipoConceptosResponseModel eu = new TipoConceptosResponseModel();
                eu.filterPayloadToSend(tipoConceptosEntity);
                tipoConceptosResponseModels.add(eu);
            }

            tipoConceptosResponse.setTipoConceptos(tipoConceptosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoConceptosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoConceptosService | list");
        log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));

        List<TipoConceptos> tipoConceptosList = tipoConceptosRepository.findAll();

        List<TipoConceptosResponseModel> tipoConceptosResponseModels = new ArrayList<>();
        for (TipoConceptos tipoConceptos : tipoConceptosList) {
            TipoConceptosResponseModel responseModel = new TipoConceptosResponseModel();
            responseModel.filterPayloadToSend(tipoConceptos);
            tipoConceptosResponseModels.add(responseModel);
        }

        TipoConceptosResponse tipoConceptosResponse = new TipoConceptosResponse();
        tipoConceptosResponse.setTipoConceptos(tipoConceptosResponseModels);

        return new ResponseEntity<>(tipoConceptosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoConceptosRequest tipoConceptosRequest) throws Exception {
        log.info("TipoConceptosService | deleteById");
        log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo conceptos a eliminar: " + tipoConceptosRequest.getCodTipoConceptos());

        int codTipoConceptos = tipoConceptosRequest.getCodTipoConceptos();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoConceptosRepository.deleteById(codTipoConceptos);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoConceptosRequest tipoConceptosRequest) {
        try {
            log.info("TipoConceptosController | save");
            log.info("__tipoConceptosRequest: " + Util.getJsonFromObject(tipoConceptosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoConceptosRequest.getCodTipoConceptos() != null && tipoConceptosRequest.getCodTipoConceptos() != 0) {
                // UPDATE
                int codTipoConceptos = tipoConceptosRequest.getCodTipoConceptos();

                Optional<TipoConceptos> optionalTipoConceptos = tipoConceptosRepository.findById(codTipoConceptos);
                if (optionalTipoConceptos.isPresent()) {
                    TipoConceptos tipoConceptosUpdate = optionalTipoConceptos.get();
                    tipoConceptosUpdate.setDescripcion(tipoConceptosRequest.getDescripcion().toUpperCase());

                    tipoConceptosRepository.save(tipoConceptosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo conceptos con el ID proporcionado no existe");
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
            log.error("Error al guardar el tipo conceptos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo vinculos: " + e.getMessage());
        }
    }

}
