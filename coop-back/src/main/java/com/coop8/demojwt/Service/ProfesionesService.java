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
import com.coop8.demojwt.Models.Profesiones;
import com.coop8.demojwt.Models.Profesiones;
import com.coop8.demojwt.PayloadModels.ProfesionesResponseModel;
import com.coop8.demojwt.Repository.ProfesionesRepository;
import com.coop8.demojwt.Request.ProfesionesRequest;
import com.coop8.demojwt.Request.ProfesionesRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.ProfesionesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfesionesService {


    @Autowired
    ProfesionesRepository profesionesRepository;

    @Autowired
    JwtService jwtService;


    public SecuredResponse newAction(@Valid @RequestBody ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesService | newAction");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(profesionesRequest.getCodProfesiones()) || profesionesRequest.getCodProfesiones() == 0) {
            // INSERT
            Profesiones profesionesInsert = new Profesiones();
            profesionesInsert.setDescripcion(profesionesRequest.getDescripcion().toUpperCase());
            profesionesInsert.setFechasys(new Date());
            profesionesInsert.setUsuariosys(usuariosys);

           profesionesRepository.save(profesionesInsert);

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

  public SecuredResponse getById(ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesService | getById");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

        ProfesionesResponse profesionesResponse = new ProfesionesResponse();

        Optional<Profesiones> profesiones = profesionesRepository.findById(profesionesRequest.getCodProfesiones());

        if (profesiones.isPresent()) {
            List<Profesiones> profesionesList = new ArrayList<>();
            profesionesList.add(profesiones.get());

            List<ProfesionesResponseModel> profesionesResponseModels = new ArrayList<>();

            for (Profesiones profesionesEntity : profesionesList) {
                ProfesionesResponseModel eu = new ProfesionesResponseModel();
                eu.filterPayloadToSend(profesionesEntity);
                profesionesResponseModels.add(eu);
            }

            profesionesResponse.setProfesiones(profesionesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(profesionesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesService | list");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

        List<Profesiones> profesionesList = profesionesRepository.findAll();

        List<ProfesionesResponseModel> profesionesResponseModels = new ArrayList<>();
        for (Profesiones profesiones : profesionesList) {
            ProfesionesResponseModel responseModel = new ProfesionesResponseModel();
            responseModel.filterPayloadToSend(profesiones);
            profesionesResponseModels.add(responseModel);
        }
        

        ProfesionesResponse profesionesResponse = new ProfesionesResponse();
        profesionesResponse.setProfesiones(profesionesResponseModels);

        return new ResponseEntity<>(profesionesResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(ProfesionesRequest profesionesRequest) throws Exception {
        log.info("ProfesionesService | deleteById");
        log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + profesionesRequest.getCodProfesiones());

        int codProfesiones = profesionesRequest.getCodProfesiones();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        profesionesRepository.deleteById(codProfesiones);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody ProfesionesRequest profesionesRequest) {
        try {
            log.info("TipVinculosController | save");
            log.info("__profesionesRequest: " + Util.getJsonFromObject(profesionesRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (profesionesRequest.getCodProfesiones() != null && profesionesRequest.getCodProfesiones() != 0) {
                // UPDATE
                int codProfesiones = profesionesRequest.getCodProfesiones();

                Optional<Profesiones> optionalProfesiones = profesionesRepository.findById(codProfesiones);
                if (optionalProfesiones.isPresent()) {
                    Profesiones profesionesUpdate = optionalProfesiones.get();
                    profesionesUpdate.setDescripcion(profesionesRequest.getDescripcion().toUpperCase());

                    profesionesRepository.save(profesionesUpdate);

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
