package com.coop8.demojwt.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Entidades;
import com.coop8.demojwt.PayloadModels.EntidadesResponseModel;
import com.coop8.demojwt.Repository.EntidadesRepository;
import com.coop8.demojwt.Request.EntidadesRequest;
import com.coop8.demojwt.Response.EntidadesResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Slf4j
public class EntidadesService {

     @Autowired
    EntidadesRepository entidadesRepository;
    
    @Autowired
    JwtService jwtService;
    public SecuredResponse getById(EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesService | getById");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        EntidadesResponse entidadesResponse = new EntidadesResponse();

        Optional<Entidades> entidad = entidadesRepository.findById(entidadesRequest.getCodEntidades());

        if (entidad.isPresent()) {
            List<Entidades> entidadesList = new ArrayList<>();
            entidadesList.add(entidad.get());

            List<EntidadesResponseModel> entidadesResponseModels = new ArrayList<>();

            for (Entidades entidadesEntity : entidadesList) {
                EntidadesResponseModel eu = new EntidadesResponseModel();
                eu.filterPayloadToSend(entidadesEntity);
                entidadesResponseModels.add(eu);
            }

            entidadesResponse.setEntidades(entidadesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(entidadesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    public ResponseEntity<?> list(EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesService | list");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        List<Entidades> entidadesList = entidadesRepository.findAll();

        List<EntidadesResponseModel> entidadesResponseModels = new ArrayList<>();
        for (Entidades entidad : entidadesList) {
            EntidadesResponseModel entidadesResponseModel = new EntidadesResponseModel();
            entidadesResponseModel.filterPayloadToSend(entidad);
            entidadesResponseModels.add(entidadesResponseModel);
        }

        EntidadesResponse entidadesResponse = new EntidadesResponse();
        entidadesResponse.setEntidades(entidadesResponseModels);

        return new ResponseEntity<>(entidadesResponse, HttpStatus.OK);
    }
    public SecuredResponse deleteById(EntidadesRequest entidadesRequest) throws Exception {
    log.info("EntidadesService | deleteById");
    log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));
    
    // Agregar impresión del ID de la Entidad
    log.info("ID de la entidad a eliminar: " + entidadesRequest.getCodEntidades());

    int CodEntidades = entidadesRequest.getCodEntidades();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    entidadesRepository.deleteById(CodEntidades);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}
     public ResponseEntity<?> save(@Valid @RequestBody EntidadesRequest entidadesRequest) {
    try {
        log.info("EntidadesController | save");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (entidadesRequest.getCodEntidades() != null && entidadesRequest.getCodEntidades() != 0) {
            
            // UPDATE
            int id = entidadesRequest.getCodEntidades();

            Optional<Entidades> optionalEntidades = entidadesRepository.findById(id);
            if (optionalEntidades.isPresent()) {
                Entidades entidadesUpdate = optionalEntidades.get();
                entidadesUpdate.setDescripcion(entidadesRequest.getDescripcion().toUpperCase());

                entidadesRepository.save(entidadesUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("La entidad con el ID proporcionado no existe");
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
        log.error("Error al guardar la entidad: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la entidad: " + e.getMessage());
    }
}
    public SecuredResponse newAction(@Valid @RequestBody EntidadesRequest entidadesRequest) throws Exception {
        log.info("EntidadesService | newAction");
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__entidadesRequest: " + Util.getJsonFromObject(entidadesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(entidadesRequest.getCodEntidades()) || entidadesRequest.getCodEntidades() == 0) {
            // INSERT
            Entidades entidadesInsert = new Entidades();
            entidadesInsert.setDescripcion(entidadesRequest.getDescripcion().toUpperCase());
            entidadesInsert.setFechasys(new Date());
            entidadesInsert.setUsuariosys(usuariosys);
            

            entidadesRepository.save(entidadesInsert);

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
