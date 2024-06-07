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
import com.coop8.demojwt.Models.TipoDocumentos;
import com.coop8.demojwt.PayloadModels.TipoDocumentosResponseModel;
import com.coop8.demojwt.Repository.TipoDocumentosRepository;
import com.coop8.demojwt.Request.TipoDocumentosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoDocumentosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoDocumentosService {

    @Autowired
    TipoDocumentosRepository tipoDocumentosRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosService | newAction");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoDocumentosRequest.getCodTipoDocumentos()) || tipoDocumentosRequest.getCodTipoDocumentos() == 0) {
            // INSERT
            TipoDocumentos tipoDocumentoInsert = new TipoDocumentos();
            tipoDocumentoInsert.setDescripcion(tipoDocumentosRequest.getDescripcion().toUpperCase());
            tipoDocumentoInsert.setFechasys(new Date());
            tipoDocumentoInsert.setUsuariosys(usuariosys);

            tipoDocumentosRepository.save(tipoDocumentoInsert);

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
            header.setTxtResultado("Se proporcionó un ID para una operación de creación, esto no es permitido");
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

    public SecuredResponse getById(TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosService | getById");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));

        TipoDocumentosResponse tipoDocumentosResponse = new TipoDocumentosResponse();

        Optional<TipoDocumentos> tipoDocumentos = tipoDocumentosRepository.findById(tipoDocumentosRequest.getCodTipoDocumentos());

        if (tipoDocumentos.isPresent()) {
            List<TipoDocumentos> tipoDocumentosList = new ArrayList<>();
            tipoDocumentosList.add(tipoDocumentos.get());

            List<TipoDocumentosResponseModel> tipoDocumentosResponseModels = new ArrayList<>();

            for (TipoDocumentos tipoDocumentosEntity : tipoDocumentosList) {
                TipoDocumentosResponseModel eu = new TipoDocumentosResponseModel();
                eu.filterPayloadToSend(tipoDocumentosEntity);
                tipoDocumentosResponseModels.add(eu);
            }

            tipoDocumentosResponse.setTipoDocumentos(tipoDocumentosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoDocumentosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosService | list");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));

        List<TipoDocumentos> tipoDocumentosList = tipoDocumentosRepository.findAll();

        List<TipoDocumentosResponseModel> tipoDocumentosResponseModels = new ArrayList<>();
        for (TipoDocumentos tipoDocumentos : tipoDocumentosList) {
            TipoDocumentosResponseModel responseModel = new TipoDocumentosResponseModel();
            responseModel.filterPayloadToSend(tipoDocumentos);
            tipoDocumentosResponseModels.add(responseModel);
        }

        TipoDocumentosResponse tipoDocumentosResponse = new TipoDocumentosResponse();
        tipoDocumentosResponse.setTipoDocumentos(tipoDocumentosResponseModels);

        return new ResponseEntity<>(tipoDocumentosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoDocumentosRequest tipoDocumentosRequest) throws Exception {
        log.info("TipoDocumentosService | deleteById");
        log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + tipoDocumentosRequest.getCodTipoDocumentos());

        int codTipoDocumentos = tipoDocumentosRequest.getCodTipoDocumentos();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoDocumentosRepository.deleteById(codTipoDocumentos);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoDocumentosRequest tipoDocumentosRequest) {
        try {
            log.info("TipoDocumentosController | save");
            log.info("__tipoDocumentosRequest: " + Util.getJsonFromObject(tipoDocumentosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoDocumentosRequest.getCodTipoDocumentos() != null && tipoDocumentosRequest.getCodTipoDocumentos() != 0) {
                // UPDATE
                int codTipoDocumentos = tipoDocumentosRequest.getCodTipoDocumentos();

                Optional<TipoDocumentos> optionalTipoDocumentos = tipoDocumentosRepository.findById(codTipoDocumentos);
                if (optionalTipoDocumentos.isPresent()) {
                    TipoDocumentos tipoDocumentosUpdate = optionalTipoDocumentos.get();
                    tipoDocumentosUpdate.setDescripcion(tipoDocumentosRequest.getDescripcion().toUpperCase());

                    tipoDocumentosRepository.save(tipoDocumentosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo vinculos con el ID proporcionado no existe");
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
            log.error("Error al guardar el tipo vinculos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo vinculos: " + e.getMessage());
        }
    }

}
