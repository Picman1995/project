package com.coop8.demojwt.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.TipoVehiculos;
import com.coop8.demojwt.PayloadModels.TipoVehiculosResponseModel;
import com.coop8.demojwt.Repository.TipoVehiculosRepository;
import com.coop8.demojwt.Request.TipoVehiculosRequest;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.TipoVehiculosResponse;
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
public class TipoVehiculosService {

    @Autowired
    TipoVehiculosRepository tipoVehiculosRepository;
    
    @Autowired
    JwtService jwtService;
    public SecuredResponse getById(TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosService | getById");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        TipoVehiculosResponse tipoVehiculosResponse = new TipoVehiculosResponse();

        Optional<TipoVehiculos> tipoVehiculo = tipoVehiculosRepository.findById(tipoVehiculosRequest.getCodTipoVehiculos());

        if (tipoVehiculo.isPresent()) {
            List<TipoVehiculos> tipoVehiculosList = new ArrayList<>();
            tipoVehiculosList.add(tipoVehiculo.get());

            List<TipoVehiculosResponseModel> tipoVehiculosResponseModels = new ArrayList<>();

            for (TipoVehiculos tipoVehiculosEntity : tipoVehiculosList) {
                TipoVehiculosResponseModel eu = new TipoVehiculosResponseModel();
                eu.filterPayloadToSend(tipoVehiculosEntity);
                tipoVehiculosResponseModels.add(eu);
            }

            tipoVehiculosResponse.setTipoVehiculos(tipoVehiculosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(tipoVehiculosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    public ResponseEntity<?> list(TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosService | list");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        List<TipoVehiculos> tipoVehiculosList = tipoVehiculosRepository.findAll();

        List<TipoVehiculosResponseModel> tipoVehiculosResponseModels = new ArrayList<>();
        for (TipoVehiculos tipoVehiculo : tipoVehiculosList) {
            TipoVehiculosResponseModel tipoVehiculosResponseModel = new TipoVehiculosResponseModel();
            tipoVehiculosResponseModel.filterPayloadToSend(tipoVehiculo);
            tipoVehiculosResponseModels.add(tipoVehiculosResponseModel);
        }

        TipoVehiculosResponse tipoVehiculosResponse = new TipoVehiculosResponse();
        tipoVehiculosResponse.setTipoVehiculos(tipoVehiculosResponseModels);

        return new ResponseEntity<>(tipoVehiculosResponse, HttpStatus.OK);
    }
    public SecuredResponse deleteById(TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
    log.info("TipoVehiculosService | deleteById");
    log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));
    
    // Agregar impresión del ID del Tipo de Vehiculos
    log.info("ID del tipo de vehiculo a eliminar: " + tipoVehiculosRequest.getCodTipoVehiculos());

    int CodTipoVehiculos = tipoVehiculosRequest.getCodTipoVehiculos();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    tipoVehiculosRepository.deleteById(CodTipoVehiculos);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}
     public ResponseEntity<?> save(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) {
    try {
        log.info("TipoVehiculosController | save");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (tipoVehiculosRequest.getCodTipoVehiculos() != null && tipoVehiculosRequest.getCodTipoVehiculos() != 0) {
            // UPDATE
            int id = tipoVehiculosRequest.getCodTipoVehiculos();

            Optional<TipoVehiculos> optionalTipoVehiculos = tipoVehiculosRepository.findById(id);
            if (optionalTipoVehiculos.isPresent()) {
                TipoVehiculos tipoVehiculosUpdate = optionalTipoVehiculos.get();
                tipoVehiculosUpdate.setDescripcion(tipoVehiculosRequest.getDescripcion().toUpperCase());

                tipoVehiculosRepository.save(tipoVehiculosUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("El tipo de Vehiculo con el ID proporcionado no existe");
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
        log.error("Error al guardar el tipo de vehiculo: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el tipo de vehiculo: " + e.getMessage());
    }
}
    public SecuredResponse newAction(@Valid @RequestBody TipoVehiculosRequest tipoVehiculosRequest) throws Exception {
        log.info("TipoVehiculosService | newAction");
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__tipoVehiculosRequest: " + Util.getJsonFromObject(tipoVehiculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(tipoVehiculosRequest.getCodTipoVehiculos()) || tipoVehiculosRequest.getCodTipoVehiculos() == 0) {
            // INSERT
            TipoVehiculos tipovehiculoInsert = new TipoVehiculos();
            tipovehiculoInsert.setDescripcion(tipoVehiculosRequest.getDescripcion().toUpperCase());
            tipovehiculoInsert.setFechasys(new Date());
            tipovehiculoInsert.setUsuariosys(usuariosys);

            tipoVehiculosRepository.save(tipovehiculoInsert);

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


