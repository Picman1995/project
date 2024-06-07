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
import com.coop8.demojwt.Models.Pais;
import com.coop8.demojwt.Models.TipoVinculos;
import com.coop8.demojwt.PayloadModels.TipoVinculosResponseModel;
import com.coop8.demojwt.Repository.TipoVinculosRespository;
import com.coop8.demojwt.Request.TipoVinculosRequest;
import com.coop8.demojwt.Response.TipoVinculosResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipoVinculosService {

    @Autowired
    TipoVinculosRespository tipoVinculosRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosService | newAction");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoVinculosRequest.getCodTipoVinculos()) || tipoVinculosRequest.getCodTipoVinculos() == 0) {
            // INSERT
            TipoVinculos tipoVinculosInsert = new TipoVinculos();
            tipoVinculosInsert.setDescripcion(tipoVinculosRequest.getDescripcion().toUpperCase());
            tipoVinculosInsert.setFechasys(new Date());
            tipoVinculosInsert.setUsuariosys(usuariosys);

            tipoVinculosRepository.save(tipoVinculosInsert);

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

    public SecuredResponse getById(TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosService | getById");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

        TipoVinculosResponse tipoVinculosResponse = new TipoVinculosResponse();

        Optional<TipoVinculos> tipoVinculos = tipoVinculosRepository.findById(tipoVinculosRequest.getCodTipoVinculos());

        if (tipoVinculos.isPresent()) {
            List<TipoVinculos> tipoVinculosList = new ArrayList<>();
            tipoVinculosList.add(tipoVinculos.get());

            List<TipoVinculosResponseModel> tipoVinculosResponseModels = new ArrayList<>();

            for (TipoVinculos tipoVinculosEntity : tipoVinculosList) {
                TipoVinculosResponseModel eu = new TipoVinculosResponseModel();
                eu.filterPayloadToSend(tipoVinculosEntity);
                tipoVinculosResponseModels.add(eu);
            }

            tipoVinculosResponse.setTipoVinculos(tipoVinculosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoVinculosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosService | list");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

        List<TipoVinculos> tipoVinculosList = tipoVinculosRepository.findAll();

        List<TipoVinculosResponseModel> tipoVinculosResponseModels = new ArrayList<>();
        for (TipoVinculos tipoVinculos : tipoVinculosList) {
            TipoVinculosResponseModel responseModel = new TipoVinculosResponseModel();
            responseModel.filterPayloadToSend(tipoVinculos);
            tipoVinculosResponseModels.add(responseModel);
        }

        TipoVinculosResponse tipoVinculosResponse = new TipoVinculosResponse();
        tipoVinculosResponse.setTipoVinculos(tipoVinculosResponseModels);

        return new ResponseEntity<>(tipoVinculosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(TipoVinculosRequest tipoVinculosRequest) throws Exception {
        log.info("TipoVinculosService | deleteById");
        log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

        // Agregar impresión del ID del país
        log.info("ID del tipo vinculo a eliminar: " + tipoVinculosRequest.getCodTipoVinculos());

        int codTipoVinculos = tipoVinculosRequest.getCodTipoVinculos();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        tipoVinculosRepository.deleteById(codTipoVinculos);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody TipoVinculosRequest tipoVinculosRequest) {
        try {
            log.info("TipVinculosController | save");
            log.info("__tipoVinculosRequest: " + Util.getJsonFromObject(tipoVinculosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (tipoVinculosRequest.getCodTipoVinculos() != null && tipoVinculosRequest.getCodTipoVinculos() != 0) {
                // UPDATE
                int codTipoVinculos = tipoVinculosRequest.getCodTipoVinculos();

                Optional<TipoVinculos> optionalTipoVinculos = tipoVinculosRepository.findById(codTipoVinculos);
                if (optionalTipoVinculos.isPresent()) {
                    TipoVinculos tipoVinculosUpdate = optionalTipoVinculos.get();
                    tipoVinculosUpdate.setDescripcion(tipoVinculosRequest.getDescripcion().toUpperCase());

                    tipoVinculosRepository.save(tipoVinculosUpdate);

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
