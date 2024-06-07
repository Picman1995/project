package com.coop8.demojwt.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.ModeloVehiculos;
import com.coop8.demojwt.PayloadModels.ModeloVehiculosResponseModel;
import com.coop8.demojwt.Repository.ModeloVehiculosRepository;
import com.coop8.demojwt.Request.ModeloVehiculosRequest;
import com.coop8.demojwt.Response.ModeloVehiculosResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Slf4j
public class ModeloVehiculosService {

    @Autowired
    ModeloVehiculosRepository modeloVehiculosRepository;
    
    @Autowired
    JwtService jwtService;
    public SecuredResponse getById(ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosService | getById");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        ModeloVehiculosResponse modeloVehiculosResponse = new ModeloVehiculosResponse();

        Optional<ModeloVehiculos> modeloVehiculo = modeloVehiculosRepository.findById(modeloVehiculosRequest.getCodModeloVehiculos());

        if (modeloVehiculo.isPresent()) {
            List<ModeloVehiculos> modeloVehiculosList = new ArrayList<>();
            modeloVehiculosList.add(modeloVehiculo.get());

            List<ModeloVehiculosResponseModel> modeloVehiculosResponseModels = new ArrayList<>();

            for (ModeloVehiculos modeloVehiculosEntity : modeloVehiculosList) {
                ModeloVehiculosResponseModel eu = new ModeloVehiculosResponseModel();
                eu.filterPayloadToSend(modeloVehiculosEntity);
                modeloVehiculosResponseModels.add(eu);
            }

            modeloVehiculosResponse.setModeloVehiculos(modeloVehiculosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(modeloVehiculosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    public ResponseEntity<?> list(ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosService | list");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        List<ModeloVehiculos> modeloVehiculosList = modeloVehiculosRepository.findAll();

        List<ModeloVehiculosResponseModel> modeloVehiculosResponseModels = new ArrayList<>();
        for (ModeloVehiculos modeloVehiculo : modeloVehiculosList) {
            ModeloVehiculosResponseModel modeloVehiculosResponseModel = new ModeloVehiculosResponseModel();
            modeloVehiculosResponseModel.filterPayloadToSend(modeloVehiculo);
            modeloVehiculosResponseModels.add(modeloVehiculosResponseModel);
        }

        ModeloVehiculosResponse modeloVehiculosResponse = new ModeloVehiculosResponse();
        modeloVehiculosResponse.setModeloVehiculos(modeloVehiculosResponseModels);

        return new ResponseEntity<>(modeloVehiculosResponse, HttpStatus.OK);
    }
    public SecuredResponse deleteById(ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
    log.info("ModeloVehiculosService | deleteById");
    log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));
    
    // Agregar impresión del ID del Modelo de Vehiculos
    log.info("ID del modelo de vehiculo a eliminar: " + modeloVehiculosRequest.getCodModeloVehiculos());

    int CodModeloVehiculos = modeloVehiculosRequest.getCodModeloVehiculos();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    modeloVehiculosRepository.deleteById(CodModeloVehiculos);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}
     public ResponseEntity<?> save(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) {
    try {
        log.info("ModeloVehiculosController | save");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (modeloVehiculosRequest.getCodModeloVehiculos() != null && modeloVehiculosRequest.getCodModeloVehiculos() != 0) {
            
            // UPDATE
            int id = modeloVehiculosRequest.getCodModeloVehiculos();

            Optional<ModeloVehiculos> optionalModeloVehiculos = modeloVehiculosRepository.findById(id);
            if (optionalModeloVehiculos.isPresent()) {
                ModeloVehiculos modeloVehiculosUpdate = optionalModeloVehiculos.get();
                modeloVehiculosUpdate.setDescripcion(modeloVehiculosRequest.getDescripcion().toUpperCase());

                modeloVehiculosRepository.save(modeloVehiculosUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("El modelo de Vehiculo con el ID proporcionado no existe");
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
        log.error("Error al guardar el modelo de vehiculo: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el modelo de vehiculo: " + e.getMessage());
    }
}
    public SecuredResponse newAction(@Valid @RequestBody ModeloVehiculosRequest modeloVehiculosRequest) throws Exception {
        log.info("ModeloVehiculosService | newAction");
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__modeloVehiculosRequest: " + Util.getJsonFromObject(modeloVehiculosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(modeloVehiculosRequest.getCodModeloVehiculos()) || modeloVehiculosRequest.getCodModeloVehiculos() == 0) {
            // INSERT
            ModeloVehiculos modelovehiculoInsert = new ModeloVehiculos();
            modelovehiculoInsert.setDescripcion(modeloVehiculosRequest.getDescripcion().toUpperCase());
            modelovehiculoInsert.setFechasys(new Date());
            modelovehiculoInsert.setUsuariosys(usuariosys);

            modeloVehiculosRepository.save(modelovehiculoInsert);

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

