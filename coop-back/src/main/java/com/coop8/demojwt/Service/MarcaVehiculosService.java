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
import com.coop8.demojwt.Models.MarcaVehiculos;
import com.coop8.demojwt.PayloadModels.MarcaVehiculosResponseModel;
import com.coop8.demojwt.Repository.MarcaVehiculosRepository;
import com.coop8.demojwt.Request.MarcaVehiculosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.MarcaVehiculosResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarcaVehiculosService {

    @Autowired
    MarcaVehiculosRepository marcaVehiculosRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody MarcaVehiculosRequest MarcaVehiculosRequest) throws Exception {
        log.info("MarcaVehiculosService | newAction");
        log.info("__MarcaVehiculosRequest: " + Util.getJsonFromObject(MarcaVehiculosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__MarcaVehiculosRequest: " + Util.getJsonFromObject(MarcaVehiculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(MarcaVehiculosRequest.getCodMarcaVehiculos())
                || MarcaVehiculosRequest.getCodMarcaVehiculos() == 0) {
            // INSERT
            MarcaVehiculos marcaVehiculosInsert = new MarcaVehiculos();
            marcaVehiculosInsert.setDescripcion(MarcaVehiculosRequest.getDescripcion().toUpperCase());
            marcaVehiculosInsert.setFechasys(new Date());
            marcaVehiculosInsert.setUsuariosys(usuariosys);

            marcaVehiculosRepository.save(marcaVehiculosInsert);

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

    public SecuredResponse getById(MarcaVehiculosRequest marcaVehiculosRequest) throws Exception {
        log.info("MarcaVehiculosService | getById");
        log.info("__marcaVehiculosRequest: " + Util.getJsonFromObject(marcaVehiculosRequest));

        MarcaVehiculosResponse marcaVehiculosResponse = new MarcaVehiculosResponse();

        Optional<MarcaVehiculos> marcaVehiculos = marcaVehiculosRepository.findById(marcaVehiculosRequest.getCodMarcaVehiculos());

        if (marcaVehiculos.isPresent()) {
            List<MarcaVehiculos> marcaVehiculosList = new ArrayList<>();
            marcaVehiculosList.add(marcaVehiculos.get());

            List<MarcaVehiculosResponseModel> marcaVehiculosResponseModels = new ArrayList<>();

            for (MarcaVehiculos marcaVehiculosEntity : marcaVehiculosList) {
                MarcaVehiculosResponseModel eu = new MarcaVehiculosResponseModel();
                eu.filterPayloadToSend(marcaVehiculosEntity);
                marcaVehiculosResponseModels.add(eu);
            }

            marcaVehiculosResponse.setMarcaVehiculos(marcaVehiculosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(marcaVehiculosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(MarcaVehiculosRequest marcaVehiculosRequest) throws Exception {
        log.info("MarcaVehiculosService | list");
        log.info("__marcaVehiculosRequest: " + Util.getJsonFromObject(marcaVehiculosRequest));

        List<MarcaVehiculos> marcaVehiculosList = marcaVehiculosRepository.findAll();

        List<MarcaVehiculosResponseModel> marcaVehiculosResponseModels = new ArrayList<>();
        for (MarcaVehiculos marcaVehiculos : marcaVehiculosList) {
            MarcaVehiculosResponseModel responseModel = new MarcaVehiculosResponseModel();
            responseModel.filterPayloadToSend(marcaVehiculos);
            marcaVehiculosResponseModels.add(responseModel);
        }

        MarcaVehiculosResponse marcaVehiculosResponse = new MarcaVehiculosResponse();
        marcaVehiculosResponse.setMarcaVehiculos(marcaVehiculosResponseModels);

        return new ResponseEntity<>(marcaVehiculosResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(MarcaVehiculosRequest marcaVehiculosRequest) throws Exception {
        log.info("MarcaVehiculosService | deleteById");
        log.info("__marcaVehiculosRequest: " + Util.getJsonFromObject(marcaVehiculosRequest));

        // Agregar impresión del ID del país
        log.info("ID de marca vehiculo a eliminar: " + marcaVehiculosRequest.getCodMarcaVehiculos());

        int codMarcaVehiculos = marcaVehiculosRequest.getCodMarcaVehiculos();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        marcaVehiculosRepository.deleteById(codMarcaVehiculos);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody MarcaVehiculosRequest marcaVehiculosRequest) {
        try {
            log.info("MarcaVehiculosController | save");
            log.info("__marcaVehiculosRequest: " + Util.getJsonFromObject(marcaVehiculosRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (marcaVehiculosRequest.getCodMarcaVehiculos() != null && marcaVehiculosRequest.getCodMarcaVehiculos() != 0) {
                // UPDATE
                int codMarcaVehiculos = marcaVehiculosRequest.getCodMarcaVehiculos();

                Optional<MarcaVehiculos> optionalMarcaVehiculos = marcaVehiculosRepository.findById(codMarcaVehiculos);
                if (optionalMarcaVehiculos.isPresent()) {
                    MarcaVehiculos marcaVehiculosUpdate = optionalMarcaVehiculos.get();
                    marcaVehiculosUpdate.setDescripcion(marcaVehiculosRequest.getDescripcion().toUpperCase());

                    marcaVehiculosRepository.save(marcaVehiculosUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("El marca de vehiculo con el ID proporcionado no existe");
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
            log.error("Error al guardar el marca vehiculos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el marca vehiculos: " + e.getMessage());
        }
    }

}
