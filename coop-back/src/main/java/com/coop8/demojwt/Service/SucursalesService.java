package com.coop8.demojwt.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Entidades;
import com.coop8.demojwt.Models.Sucursales;
import com.coop8.demojwt.PayloadModels.EntidadesResponseModel;
import com.coop8.demojwt.PayloadModels.SucursalesResponseModel;
import com.coop8.demojwt.Repository.EntidadesRepository;
import com.coop8.demojwt.Repository.SucursalesRepository;
import com.coop8.demojwt.Request.EntidadesRequest;
import com.coop8.demojwt.Request.SucursalesRequest;
import com.coop8.demojwt.Response.EntidadesResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.SucursalesResponse;
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
public class SucursalesService {

     @Autowired
    SucursalesRepository sucursalesRepository;
    
    @Autowired
    JwtService jwtService;
    public SecuredResponse getById(SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesService | getById");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        SucursalesResponse sucursalesResponse = new SucursalesResponse();

        Optional<Sucursales> sucursal = sucursalesRepository.findById(sucursalesRequest.getCodSucursales());

        if (sucursal.isPresent()) {
            List<Sucursales> sucursalesList = new ArrayList<>();
            sucursalesList.add(sucursal.get());

            List<SucursalesResponseModel> sucursalesResponseModels = new ArrayList<>();

            for (Sucursales sucursalesEntity : sucursalesList) {
                SucursalesResponseModel eu = new SucursalesResponseModel();
                eu.filterPayloadToSend(sucursalesEntity);
                sucursalesResponseModels.add(eu);
            }

            sucursalesResponse.setSucursales(sucursalesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(sucursalesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    public ResponseEntity<?> list(SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesService | list");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        List<Sucursales> sucursalesList = sucursalesRepository.findAll();

        List<SucursalesResponseModel> sucursalesResponseModels = new ArrayList<>();
        for (Sucursales sucursal : sucursalesList) {
            SucursalesResponseModel sucursalesResponseModel = new SucursalesResponseModel();
            sucursalesResponseModel.filterPayloadToSend(sucursal);
            sucursalesResponseModels.add(sucursalesResponseModel);
        }

        SucursalesResponse sucursalesResponse = new SucursalesResponse();
        sucursalesResponse.setSucursales(sucursalesResponseModels);

        return new ResponseEntity<>(sucursalesResponse, HttpStatus.OK);
    }
    public SecuredResponse deleteById(SucursalesRequest sucursalesRequest) throws Exception {
    log.info("SucursalesService | deleteById");
    log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));
    
    // Agregar impresión del ID de la Sucursal
    log.info("ID de la sucursal a eliminar: " + sucursalesRequest.getCodSucursales());

    int CodSucursales = sucursalesRequest.getCodSucursales();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    sucursalesRepository.deleteById(CodSucursales);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    // Creating the token with extra claims
    String token = Util.getJsonFromObject(response);
    securedResponse.setData(token);

    return securedResponse;
}
     public ResponseEntity<?> save(@Valid @RequestBody SucursalesRequest sucursalesRequest) {
    try {
        log.info("SucursalesController | save");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        if (sucursalesRequest.getCodSucursales() != null && sucursalesRequest.getCodSucursales() != 0) {
            
            // UPDATE
            int id = sucursalesRequest.getCodSucursales();

            Optional<Sucursales> optionalSucursales = sucursalesRepository.findById(id);
            if (optionalSucursales.isPresent()) {
                Sucursales sucursalesUpdate = optionalSucursales.get();
                sucursalesUpdate.setDescripcion(sucursalesRequest.getDescripcion().toUpperCase());

                sucursalesRepository.save(sucursalesUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);

                return ResponseEntity.ok().body(securedResponse);
            } else {
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("La sucursal con el ID proporcionado no existe");
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
        log.error("Error al guardar la sucursal: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la sucursal: " + e.getMessage());
    }
}
    public SecuredResponse newAction(@Valid @RequestBody SucursalesRequest sucursalesRequest) throws Exception {
        log.info("SucursalesService | newAction");
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__sucursalesRequest: " + Util.getJsonFromObject(sucursalesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(sucursalesRequest.getCodSucursales()) || sucursalesRequest.getCodSucursales() == 0) {
            // INSERT
            Sucursales sucursalesInsert = new Sucursales();
            sucursalesInsert.setDescripcion(sucursalesRequest.getDescripcion().toUpperCase());
            sucursalesInsert.setFechasys(new Date());
            sucursalesInsert.setUsuariosys(usuariosys);
            

            sucursalesRepository.save(sucursalesInsert);

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
