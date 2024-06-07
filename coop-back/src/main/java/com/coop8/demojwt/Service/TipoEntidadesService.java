package com.coop8.demojwt.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.TipoEntidades;
import com.coop8.demojwt.PayloadModels.TipoEntidadesResponseModel;
import com.coop8.demojwt.Repository.TipoEntidadesRepository;
import com.coop8.demojwt.Request.TipoEntidadesRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoEntidadesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author jacuna
 */
@Service
@Slf4j
public class TipoEntidadesService {

    @Autowired
    TipoEntidadesRepository tipoEntidadesRepository;
    
    @Autowired
    JwtService jwtService;
    public SecuredResponse getById(TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesService | getById");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        TipoEntidadesResponse tipoEntidadesResponse = new TipoEntidadesResponse();

        Optional<TipoEntidades> tipoEntidad = tipoEntidadesRepository.findById(tipoEntidadesRequest.getCodTipoEntidades());

        if (tipoEntidad.isPresent()) {
            List<TipoEntidades> tipoEntidadesList = new ArrayList<>();
            tipoEntidadesList.add(tipoEntidad.get());

            List<TipoEntidadesResponseModel> tipoEntidadesResponseModels = new ArrayList<>();

            for (TipoEntidades tipoEntidadesEntity : tipoEntidadesList) {
                TipoEntidadesResponseModel eu = new TipoEntidadesResponseModel();
                eu.filterPayloadToSend(tipoEntidadesEntity);
                tipoEntidadesResponseModels.add(eu);
            }

            tipoEntidadesResponse.setTipoEntidades(tipoEntidadesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoEntidadesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    public ResponseEntity<?> list(TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesService | list");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        List<TipoEntidades> tipoEntidadesList = tipoEntidadesRepository.findAll();

        List<TipoEntidadesResponseModel> tipoEntidadesResponseModels = new ArrayList<>();
        for (TipoEntidades tipoEntidad : tipoEntidadesList) {
            TipoEntidadesResponseModel tipoEntidadesResponseModel = new TipoEntidadesResponseModel();
            tipoEntidadesResponseModel.filterPayloadToSend(tipoEntidad);
            tipoEntidadesResponseModels.add(tipoEntidadesResponseModel);
        }

        TipoEntidadesResponse tipoEntidadesResponse = new TipoEntidadesResponse();
        tipoEntidadesResponse.setTipoEntidades(tipoEntidadesResponseModels);

        return new ResponseEntity<>(tipoEntidadesResponse, HttpStatus.OK);
    }
    public SecuredResponse deleteById(TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
    log.info("TipoEntidadesService | deleteById");
    log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));
    
    // Agregar impresión del ID del Tipo de Entidad
    log.info("ID del tipo de entidad a eliminar: " + tipoEntidadesRequest.getCodTipoEntidades());

    int CodTipoEntidades = tipoEntidadesRequest.getCodTipoEntidades();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    tipoEntidadesRepository.deleteById(CodTipoEntidades);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}
     public ResponseEntity<?> save(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) {
    try {
        log.info("TipoEntidadesController | save");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (tipoEntidadesRequest.getCodTipoEntidades() != null && tipoEntidadesRequest.getCodTipoEntidades() != 0) {
            // UPDATE
            int id = tipoEntidadesRequest.getCodTipoEntidades();

            Optional<TipoEntidades> optionalTipoEntidades = tipoEntidadesRepository.findById(id);
            if (optionalTipoEntidades.isPresent()) {
                TipoEntidades tipoEntidadesUpdate = optionalTipoEntidades.get();
                tipoEntidadesUpdate.setDescripcion(tipoEntidadesRequest.getDescripcion().toUpperCase());

                tipoEntidadesRepository.save(tipoEntidadesUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("El tipo de entidad con el ID proporcionado no existe");
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
        log.error("Error al guardar el tipo de entidad: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el tipo de entidad: " + e.getMessage());
    }
}
    public SecuredResponse newAction(@Valid @RequestBody TipoEntidadesRequest tipoEntidadesRequest) throws Exception {
        log.info("TipoEntidadesService | newAction");
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoEntidadesRequest: " + Util.getJsonFromObject(tipoEntidadesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoEntidadesRequest.getCodTipoEntidades()) || tipoEntidadesRequest.getCodTipoEntidades() == 0) {
            // INSERT
            TipoEntidades tipoentidadInsert = new TipoEntidades();
            tipoentidadInsert.setDescripcion(tipoEntidadesRequest.getDescripcion().toUpperCase());
            tipoentidadInsert.setFechasys(new Date());
            tipoentidadInsert.setUsuariosys(usuariosys);

            tipoEntidadesRepository.save(tipoentidadInsert);

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
}


