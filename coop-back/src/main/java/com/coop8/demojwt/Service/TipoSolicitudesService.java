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
import com.coop8.demojwt.Models.TipoSolicitudes;
import com.coop8.demojwt.PayloadModels.TipoSolicitudesResponseModel;
import com.coop8.demojwt.Repository.TipoSolicitudesRepository;
import com.coop8.demojwt.Request.TipoSolicitudesRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoSolicitudesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoSolicitudesService {

    @Autowired
    TipoSolicitudesRepository tipoSolicitudesRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest)
            throws Exception {
        log.info("TipoSolicitudesService | newAction");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoSolicitudesRequest.getCodTipoSolicitudes())
                || tipoSolicitudesRequest.getCodTipoSolicitudes() == 0) {
            // INSERT
            TipoSolicitudes tipoSolicitudesInsert = new TipoSolicitudes();
            tipoSolicitudesInsert.setDescripcion(tipoSolicitudesRequest.getDescripcion().toUpperCase());
            tipoSolicitudesInsert.setFechasys(new Date());
            tipoSolicitudesInsert.setUsuariosys(usuariosys);

            tipoSolicitudesRepository.save(tipoSolicitudesInsert);

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

    public SecuredResponse getById(TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesService | getById");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

        TipoSolicitudesResponse tipoSolicitudesResponse = new TipoSolicitudesResponse();

        Optional<TipoSolicitudes> tipoSolicitudes = tipoSolicitudesRepository.findById(tipoSolicitudesRequest.getCodTipoSolicitudes());

        if (tipoSolicitudes.isPresent()) {
            List<TipoSolicitudes> tipoSolicitudesList = new ArrayList<>();
            tipoSolicitudesList.add(tipoSolicitudes.get());

            List<TipoSolicitudesResponseModel> tipoSolicitudesResponseModels = new ArrayList<>();

            for (TipoSolicitudes tipoSolicitudesEntity : tipoSolicitudesList) {
                TipoSolicitudesResponseModel eu = new TipoSolicitudesResponseModel();
                eu.filterPayloadToSend(tipoSolicitudesEntity);
                tipoSolicitudesResponseModels.add(eu);
            }

            tipoSolicitudesResponse.setTipoSolicitudes(tipoSolicitudesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoSolicitudesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesService | list");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

        List<TipoSolicitudes> tipoSolicitudesList = tipoSolicitudesRepository.findAll();

        List<TipoSolicitudesResponseModel> tipoSolicitudesResponseModels = new ArrayList<>();
        for (TipoSolicitudes tipoSolicitudes : tipoSolicitudesList) {
            TipoSolicitudesResponseModel responseModel = new TipoSolicitudesResponseModel();
            responseModel.filterPayloadToSend(tipoSolicitudes);
            tipoSolicitudesResponseModels.add(responseModel);
        }

        TipoSolicitudesResponse tipoSolicitudesResponse = new TipoSolicitudesResponse();
        tipoSolicitudesResponse.setTipoSolicitudes(tipoSolicitudesResponseModels);

        return new ResponseEntity<>(tipoSolicitudesResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoSolicitudesRequest tipoSolicitudesRequest) throws Exception {
        log.info("TipoSolicitudesService | deleteById");
        log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + tipoSolicitudesRequest.getCodTipoSolicitudes());

        int codTipoSolicitudes = tipoSolicitudesRequest.getCodTipoSolicitudes();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoSolicitudesRepository.deleteById(codTipoSolicitudes);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoSolicitudesRequest tipoSolicitudesRequest) {
        try {
            log.info("TipSolicitudesController | save");
            log.info("__tipoSolicitudesRequest: " + Util.getJsonFromObject(tipoSolicitudesRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoSolicitudesRequest.getCodTipoSolicitudes() != null && tipoSolicitudesRequest.getCodTipoSolicitudes() != 0) {
                // UPDATE
                int codTipoSolicitudes = tipoSolicitudesRequest.getCodTipoSolicitudes();

                Optional<TipoSolicitudes> optionalTipoSolicitudes = tipoSolicitudesRepository.findById(codTipoSolicitudes);
                if (optionalTipoSolicitudes.isPresent()) {
                    TipoSolicitudes tipoSolicitudesUpdate = optionalTipoSolicitudes.get();
                    tipoSolicitudesUpdate.setDescripcion(tipoSolicitudesRequest.getDescripcion().toUpperCase());

                    tipoSolicitudesRepository.save(tipoSolicitudesUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El tipo solicitudes con el ID proporcionado no existe");
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
            log.error("Error al guardar el tipo solicitudes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el tipo solicitudes: " + e.getMessage());
        }
    }

}
