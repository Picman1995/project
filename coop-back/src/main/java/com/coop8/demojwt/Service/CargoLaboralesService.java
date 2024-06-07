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
import com.coop8.demojwt.Models.CargoLaborales;

import com.coop8.demojwt.PayloadModels.CargoLaboralesResponseModel;
import com.coop8.demojwt.Repository.CargoLaboralesRespository;
import com.coop8.demojwt.Request.CargoLaboralesRequest;

import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Response.CargoLaboralesResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CargoLaboralesService {

    @Autowired
    CargoLaboralesRespository cargoLaboralesRepository;

    @Autowired
    JwtService jwtService;

    public SecuredResponse newAction(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesService | newAction");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

        // Decodificamos el dataRequest y obtenemos el Payload
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario de SecurityContextHolder
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si es un INSERT
        if (Util.isNullOrEmpty(cargoLaboralesRequest.getCodCargoLaborales())
                || cargoLaboralesRequest.getCodCargoLaborales() == 0) {
            // INSERT
            CargoLaborales cargoLaboralesInsert = new CargoLaborales();
            cargoLaboralesInsert.setDescripcion(cargoLaboralesRequest.getDescripcion().toUpperCase());
            cargoLaboralesInsert.setFechasys(new Date());
            cargoLaboralesInsert.setUsuariosys(usuariosys);

            cargoLaboralesRepository.save(cargoLaboralesInsert);

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

    public SecuredResponse getById(CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesService | getById");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

        CargoLaboralesResponse cargoLaboralesResponse = new CargoLaboralesResponse();

        Optional<CargoLaborales> cargoLaborales = cargoLaboralesRepository.findById(cargoLaboralesRequest.getCodCargoLaborales());

        if (cargoLaborales.isPresent()) {
            List<CargoLaborales> cargoLaboralesList = new ArrayList<>();
            cargoLaboralesList.add(cargoLaborales.get());

            List<CargoLaboralesResponseModel> cargoLaboralesResponseModels = new ArrayList<>();

            for (CargoLaborales cargoLaboralesEntity : cargoLaboralesList) {
                CargoLaboralesResponseModel eu = new CargoLaboralesResponseModel();
                eu.filterPayloadToSend(cargoLaboralesEntity);
                cargoLaboralesResponseModels.add(eu);
            }

            cargoLaboralesResponse.setCargoLaborales(cargoLaboralesResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(cargoLaboralesResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> list(CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesService | list");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

        List<CargoLaborales> cargoLaboralesList = cargoLaboralesRepository.findAll();

        List<CargoLaboralesResponseModel> cargoLaboralesResponseModels = new ArrayList<>();
        for (CargoLaborales cargoLaborales : cargoLaboralesList) {
            CargoLaboralesResponseModel responseModel = new CargoLaboralesResponseModel();
            responseModel.filterPayloadToSend(cargoLaborales);
            cargoLaboralesResponseModels.add(responseModel);
        }

        CargoLaboralesResponse cargoLaboralesResponse = new CargoLaboralesResponse();
        cargoLaboralesResponse.setCargoLaborales(cargoLaboralesResponseModels);

        return new ResponseEntity<>(cargoLaboralesResponse, HttpStatus.OK);
    }

    @Transactional
    public SecuredResponse deleteById(CargoLaboralesRequest cargoLaboralesRequest) throws Exception {
        log.info("CargoLaboralesService | deleteById");
        log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

        // Agregar impresión del ID del país
        log.info("ID cargo laborales a eliminar: " + cargoLaboralesRequest.getCodCargoLaborales());

        int codCargoLaborales = cargoLaboralesRequest.getCodCargoLaborales();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        cargoLaboralesRepository.deleteById(codCargoLaborales);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

    public ResponseEntity<?> save(@Valid @RequestBody CargoLaboralesRequest cargoLaboralesRequest) {
        try {
            log.info("CargoLaboralesController | save");
            log.info("__cargoLaboralesRequest: " + Util.getJsonFromObject(cargoLaboralesRequest));

            ResponseHeader header = new ResponseHeader();
            Response response = new Response();
            SecuredResponse securedResponse = new SecuredResponse();

            String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

            if (cargoLaboralesRequest.getCodCargoLaborales() != null && cargoLaboralesRequest.getCodCargoLaborales() != 0) {
                // UPDATE
                int codCargoLaborales = cargoLaboralesRequest.getCodCargoLaborales();

                Optional<CargoLaborales> optionalCargoLaborales = cargoLaboralesRepository.findById(codCargoLaborales);
                if (optionalCargoLaborales.isPresent()) {
                    CargoLaborales cargoLaboralesUpdate = optionalCargoLaborales.get();
                    cargoLaboralesUpdate.setDescripcion(cargoLaboralesRequest.getDescripcion().toUpperCase());

                    cargoLaboralesRepository.save(cargoLaboralesUpdate);

                    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                    response.setHeader(header);
                    response.setData(null);

                    String token = jwtService.getDataFromPayload(response);
                    securedResponse.setData(token);

                    return ResponseEntity.ok().body(securedResponse);
                } else {
                    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                    header.setTxtResultado("Cargo laborales con el ID proporcionado no existe");
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
            log.error("Error al guardar cargo laborales: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar cargo laborales: " + e.getMessage());
        }
    }

}
