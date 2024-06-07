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
import com.coop8.demojwt.Models.CategoriaSocios;
import com.coop8.demojwt.PayloadModels.CategoriaSociosResponseModel;
import com.coop8.demojwt.Repository.CategoriaSociosRepository;
import com.coop8.demojwt.Request.CategoriaSociosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.CategoriaSociosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoriaSociosService {

    @Autowired
    CategoriaSociosRepository categoriaSociosRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest)
            throws Exception {
        log.info("CategoriaService | newAction");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(categoriaSociosRequest.getCodCategoriaSocios())
                || categoriaSociosRequest.getCodCategoriaSocios() == 0) {
            // INSERT
            CategoriaSocios categoriaSociosInsert = new CategoriaSocios();
            categoriaSociosInsert.setDescripcion(categoriaSociosRequest.getDescripcion().toUpperCase());
            categoriaSociosInsert.setFechasys(new Date());
            categoriaSociosInsert.setUsuariosys(usuariosys);

            categoriaSociosRepository.save(categoriaSociosInsert);

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

    public SecuredResponse getById(CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSociosService | getById");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

        CategoriaSociosResponse categoriaSociosResponse = new CategoriaSociosResponse();

        Optional<CategoriaSocios> categoriaSocios = categoriaSociosRepository.findById(categoriaSociosRequest.getCodCategoriaSocios());

        if (categoriaSocios.isPresent()) {
            List<CategoriaSocios> categoriaSociosList = new ArrayList<>();
            categoriaSociosList.add(categoriaSocios.get());

            List<CategoriaSociosResponseModel> categoriaSociosResponseModels = new ArrayList<>();

            for (CategoriaSocios categoriaSociosEntity : categoriaSociosList) {
                CategoriaSociosResponseModel eu = new CategoriaSociosResponseModel();
                eu.filterPayloadToSend(categoriaSociosEntity);
                categoriaSociosResponseModels.add(eu);
            }

            categoriaSociosResponse.setCategoriaSocios(categoriaSociosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(categoriaSociosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSociosService | list");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

        List<CategoriaSocios> categoriaSociosList = categoriaSociosRepository.findAll();

        List<CategoriaSociosResponseModel> categoriaSociosResponseModels = new ArrayList<>();
        for (CategoriaSocios categoriaSocios : categoriaSociosList) {
            CategoriaSociosResponseModel responseModel = new CategoriaSociosResponseModel();
            responseModel.filterPayloadToSend(categoriaSocios);
            categoriaSociosResponseModels.add(responseModel);
        }

        CategoriaSociosResponse categoriaSociosResponse = new CategoriaSociosResponse();
        categoriaSociosResponse.setCategoriaSocios(categoriaSociosResponseModels);

        return new ResponseEntity<>(categoriaSociosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(CategoriaSociosRequest categoriaSociosRequest) throws Exception {
        log.info("CategoriaSociosService | deleteById");
        log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

        // Agregar impresión del ID del país
        log.info("ID Categoria socio a eliminar: " + categoriaSociosRequest.getCodCategoriaSocios());

        int codCategoriaSocios = categoriaSociosRequest.getCodCategoriaSocios();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        categoriaSociosRepository.deleteById(codCategoriaSocios);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody CategoriaSociosRequest categoriaSociosRequest) {
        try {
            log.info("CategoriaSociosController | save");
            log.info("__categoriaSociosRequest: " + Util.getJsonFromObject(categoriaSociosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (categoriaSociosRequest.getCodCategoriaSocios() != null && categoriaSociosRequest.getCodCategoriaSocios() != 0) {
                // UPDATE
                int codCategoriaSocios = categoriaSociosRequest.getCodCategoriaSocios();

                Optional<CategoriaSocios> optionalCategoriaSocios = categoriaSociosRepository.findById(codCategoriaSocios);
                if (optionalCategoriaSocios.isPresent()) {
                    CategoriaSocios categoriaSociosUpdate = optionalCategoriaSocios.get();
                    categoriaSociosUpdate.setDescripcion(categoriaSociosRequest.getDescripcion().toUpperCase());

                    categoriaSociosRepository.save(categoriaSociosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("Categoria Socio con el ID proporcionado no existe");
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
            log.error("Error al guardar el Categoria Socios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el Categoria Socios: " + e.getMessage());
        }
    }

}
