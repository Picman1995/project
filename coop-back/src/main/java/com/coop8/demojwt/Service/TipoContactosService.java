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
import com.coop8.demojwt.Models.TipoContactos;
import com.coop8.demojwt.Models.TipoContactos;
import com.coop8.demojwt.PayloadModels.TipoContactosResponseModel;
import com.coop8.demojwt.Repository.TipoContactosRepository;
import com.coop8.demojwt.Request.TipoContactosRequest;
import com.coop8.demojwt.Request.TipoContactosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoContactosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoContactosService {

     @Autowired
    TipoContactosRepository tipoContactosRepository;

    @Autowired
    JwtService jwtService;



     public SecuredResponse newAction(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosService | newAction");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoContactosRequest.getCodTipoContactos()) || tipoContactosRequest.getCodTipoContactos() == 0) {
            // INSERT
            TipoContactos tipoContactoInsert = new TipoContactos();
            tipoContactoInsert.setDescripcion(tipoContactosRequest.getDescripcion().toUpperCase());
            tipoContactoInsert.setFechasys(new Date());
            tipoContactoInsert.setUsuariosys(usuariosys);

            tipoContactosRepository.save(tipoContactoInsert);

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


    public SecuredResponse getById(TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosService | getById");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

        TipoContactosResponse tipoContactosResponse = new TipoContactosResponse();

        Optional<TipoContactos> tipoContactos = tipoContactosRepository.findById(tipoContactosRequest.getCodTipoContactos());

        if (tipoContactos.isPresent()) {
            List<TipoContactos> tipoContactosList = new ArrayList<>();
            tipoContactosList.add(tipoContactos.get());

            List<TipoContactosResponseModel> tipoContactosResponseModels = new ArrayList<>();

            for (TipoContactos tipoContactosEntity : tipoContactosList) {
                TipoContactosResponseModel eu = new TipoContactosResponseModel();
                eu.filterPayloadToSend(tipoContactosEntity);
                tipoContactosResponseModels.add(eu);
            }

            tipoContactosResponse.setTipoContactos(tipoContactosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoContactosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosService | list");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

        List<TipoContactos> tipoContactosList = tipoContactosRepository.findAll();

        List<TipoContactosResponseModel> tipoContactosResponseModels = new ArrayList<>();
        for (TipoContactos tipoContactos : tipoContactosList) {
            TipoContactosResponseModel responseModel = new TipoContactosResponseModel();
            responseModel.filterPayloadToSend(tipoContactos);
            tipoContactosResponseModels.add(responseModel);
        }
        

        TipoContactosResponse tipoContactosResponse = new TipoContactosResponse();
        tipoContactosResponse.setTipoContactos(tipoContactosResponseModels);

        return new ResponseEntity<>(tipoContactosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoContactosRequest tipoContactosRequest) throws Exception {
        log.info("TipoContactosService | deleteById");
        log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + tipoContactosRequest.getCodTipoContactos());

        int codTipoContactos = tipoContactosRequest.getCodTipoContactos();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoContactosRepository.deleteById(codTipoContactos);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoContactosRequest tipoContactosRequest) {
        try {
            log.info("TipoContactosController | save");
            log.info("__tipoContactosRequest: " + Util.getJsonFromObject(tipoContactosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoContactosRequest.getCodTipoContactos() != null && tipoContactosRequest.getCodTipoContactos() != 0) {
                // UPDATE
                int codTipoContactos = tipoContactosRequest.getCodTipoContactos();

                Optional<TipoContactos> optionalTipoContactos = tipoContactosRepository.findById(codTipoContactos);
                if (optionalTipoContactos.isPresent()) {
                    TipoContactos tipoContactosUpdate = optionalTipoContactos.get();
                    tipoContactosUpdate.setDescripcion(tipoContactosRequest.getDescripcion().toUpperCase());

                    tipoContactosRepository.save(tipoContactosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo contactos con el ID proporcionado no existe");
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
            log.error("Error al guardar el tipo contactos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo contactos: " + e.getMessage());
        }
    }



}
