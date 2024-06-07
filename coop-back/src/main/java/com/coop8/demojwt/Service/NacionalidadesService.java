package com.coop8.demojwt.Service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Nacionalidades;
import com.coop8.demojwt.PayloadModels.NacionalidadesResponseModel;
import com.coop8.demojwt.Repository.NacionalidadesRepository;
import com.coop8.demojwt.Request.NacionalidadesRequest;
import com.coop8.demojwt.Response.NacionalidadesResponse;
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
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class NacionalidadesService {

    @Autowired
    NacionalidadesRepository nacionalidadesRepository;

    @Autowired
    JwtService jwtService;


    public SecuredResponse newAction(@Valid @RequestBody NacionalidadesRequest nacionalidadesRequest) throws Exception {
        log.info("NacionalidadesService | newAction");
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(nacionalidadesRequest.getId()) || nacionalidadesRequest.getId() == 0) {
            // INSERT
            Nacionalidades nacionalidadInsert = new Nacionalidades();
            nacionalidadInsert.setDescripcion(nacionalidadesRequest.getDescripcion().toUpperCase());
            nacionalidadInsert.setFechasys(new Date());
            nacionalidadInsert.setUsuariosys(usuariosys);

            nacionalidadesRepository.save(nacionalidadInsert);

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
    
    public ResponseEntity<?> save(@Valid @RequestBody NacionalidadesRequest nacionalidadRequest) {
    try {
        log.info("NacionalidadController | save");
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        Nacionalidades nacionalidad = new Nacionalidades();
        nacionalidad.setDescripcion(nacionalidadRequest.getDescripcion().toUpperCase());

        // Guardar la nueva nacionalidad
        nacionalidadesRepository.save(nacionalidad);

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        String token = jwtService.getDataFromPayload(response);
        securedResponse.setData(token);

        return ResponseEntity.ok().body(securedResponse);
    } catch (Exception e) {
        log.error("Error al guardar la nacionalidad: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la nacionalidad: " + e.getMessage());
    }
}
    
@Transactional
public ResponseEntity<?> deleteById(@RequestBody NacionalidadesRequest nacionalidadRequest) {
    try {
        log.info("NacionalidadService | deleteById");
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadRequest));
        
        int idNacionalidades = nacionalidadRequest.getId();
        
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        nacionalidadesRepository.deleteById(idNacionalidades);
        
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return ResponseEntity.ok().body(securedResponse);
    } catch (Exception e) {
        log.error("Error al eliminar la nacionalidad: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la nacionalidad: " + e.getMessage());
    }
}


public ResponseEntity<?> list(NacionalidadesRequest nacionalidadesRequest) {
    try {
        log.info("NacionalidadService | list");
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadesRequest));

        // Aquí puedes implementar la lógica para procesar la solicitud según los datos proporcionados en el objeto NacionalidadesRequest
        // Por ejemplo, puedes filtrar la lista de nacionalidades según los parámetros proporcionados en el objeto nacionalidadesRequest

        List<Nacionalidades> nacionalidadesList = nacionalidadesRepository.findAll();

        List<NacionalidadesResponseModel> nacionalidadesResponseModels = new ArrayList<>();
        for (Nacionalidades nacionalidades : nacionalidadesList) {
            NacionalidadesResponseModel responseModel = new NacionalidadesResponseModel();
            responseModel.filterPayloadToSend(nacionalidades);
            nacionalidadesResponseModels.add(responseModel);
        }

        NacionalidadesResponse nacionalidadesResponse = new NacionalidadesResponse();
        nacionalidadesResponse.setNacionalidades(nacionalidadesResponseModels);

        return new ResponseEntity<>(nacionalidadesResponse, HttpStatus.OK);
    } catch (Exception e) {
        log.error("Error al obtener la lista de nacionalidades: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al obtener la lista de nacionalidades: " + e.getMessage());
    }
}

public SecuredResponse getById(NacionalidadesRequest nacionalidadesRequest) {
    try {
        log.info("NacionalidadService | getById");
        log.info("__nacionalidadesRequest: " + Util.getJsonFromObject(nacionalidadesRequest));

        NacionalidadesResponse nacionalidadesResponse = new NacionalidadesResponse();

        Optional<Nacionalidades> nacionalidad = nacionalidadesRepository.findById(nacionalidadesRequest.getId());

        if (nacionalidad.isPresent()) {
            List<Nacionalidades> nacionalidadesList = new ArrayList<>();
            nacionalidadesList.add(nacionalidad.get());

            List<NacionalidadesResponseModel> nacionalidadesResponseModels = new ArrayList<>();

            for (Nacionalidades nacionalidades : nacionalidadesList) {
                NacionalidadesResponseModel model = new NacionalidadesResponseModel();
                model.filterPayloadToSend(nacionalidades);
                nacionalidadesResponseModels.add(model);
            }

            nacionalidadesResponse.setNacionalidades(nacionalidadesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(nacionalidadesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    } catch (Exception e) {
        log.error("Error al obtener la nacionalidad por ID: " + e.getMessage());
        ResponseHeader header = new ResponseHeader();
        header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
        header.setTxtResultado("Error al obtener la nacionalidad por ID");
        Response response = new Response();
        response.setHeader(header);
        SecuredResponse securedResponse = new SecuredResponse();
        securedResponse.setData(Util.getJsonFromObject(response));
        return securedResponse;
    }
}





}

