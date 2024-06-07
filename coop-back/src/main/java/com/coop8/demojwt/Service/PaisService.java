package com.coop8.demojwt.Service;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Pais;
import com.coop8.demojwt.Repository.PaisRepository;
import com.coop8.demojwt.Request.PaisRequest;
import com.coop8.demojwt.Response.PaisResponse;
import com.coop8.demojwt.PayloadModels.PaisResponseModel;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author picman
 */
@Service
@Slf4j
public class PaisService {

    @Autowired
    PaisRepository paisRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse getById(PaisRequest paisRequest) throws Exception {
        log.info("PaisService | getById");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));

        PaisResponse paisResponse = new PaisResponse();

        Optional<Pais> pais = paisRepository.findById(paisRequest.getCodPais());

        if (pais.isPresent()) {
            List<Pais> paisList = new ArrayList<>();
            paisList.add(pais.get());

            List<PaisResponseModel> paisResponseModels = new ArrayList<>();

            for (Pais paisEntity : paisList) {
                PaisResponseModel eu = new PaisResponseModel();
                eu.filterPayloadToSend(paisEntity);
                paisResponseModels.add(eu);
            }

            paisResponse.setPaises(paisResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(paisResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(PaisRequest paisRequest) throws Exception {
        log.info("PaisService | list");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));

        List<Pais> paisList = paisRepository.findAll();

        List<PaisResponseModel> paisResponseModels = new ArrayList<>();
        for (Pais pais : paisList) {
            PaisResponseModel responseModel = new PaisResponseModel();
            responseModel.filterPayloadToSend(pais);
            paisResponseModels.add(responseModel);
        }

        PaisResponse paisResponse = new PaisResponse();
        paisResponse.setPaises(paisResponseModels);

        return new ResponseEntity<>(paisResponse, HttpStatus.OK);
    }

@Transactional
public SecuredResponse deleteById(PaisRequest paisRequest) throws Exception {
    log.info("PaisService | deleteById");
    log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));
    
    // Agregar impresión del ID del país
    log.info("ID del país a eliminar: " + paisRequest.getCodPais());

    int codPais = paisRequest.getCodPais();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    paisRepository.deleteById(codPais);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}




    public ResponseEntity<?> save(@Valid @RequestBody PaisRequest paisRequest) {
    try {
        log.info("PaisController | save");
        log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (paisRequest.getCodPais() != null && paisRequest.getCodPais() != 0) {
            // UPDATE
            int codPais = paisRequest.getCodPais();

            Optional<Pais> optionalPais = paisRepository.findById(codPais);
            if (optionalPais.isPresent()) {
                Pais paisUpdate = optionalPais.get();
                paisUpdate.setDescripcion(paisRequest.getDescripcion().toUpperCase());

                paisRepository.save(paisUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("El país con el ID proporcionado no existe");
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
        log.error("Error al guardar el país: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el país: " + e.getMessage());
    }
}

public SecuredResponse newAction(@Valid @RequestBody PaisRequest paisRequest) throws Exception {
    log.info("PaisService | newAction");
    log.info("__paisRequest: " + Util.getJsonFromObject(paisRequest));

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    // Obtener el usuario autenticado
    String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

    if (paisRequest.getCodPais() == null || paisRequest.getCodPais() == 0) {
        // INSERT
        Pais paisInsert = new Pais();
        paisInsert.setDescripcion(paisRequest.getDescripcion().toUpperCase());
        paisInsert.setUsuariosys(usuariosys);  // Establecer el usuario autenticado

        paisRepository.save(paisInsert);

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        String token = jwtService.getDataFromPayload(response);
        securedResponse.setData(token);

    } else {
        // Se proporcionó un ID para una operación de creación
        header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
        header.setTxtResultado("Se proporcionó un ID para una operación de creación");
        response.setHeader(header);
        response.setData(null);

        String token = jwtService.getDataFromPayload(response);
        securedResponse.setData(token);
    }

    return securedResponse;
}
}
