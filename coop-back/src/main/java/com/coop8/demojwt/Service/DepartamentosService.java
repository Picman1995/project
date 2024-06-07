package com.coop8.demojwt.Service;

import com.coop8.demojwt.Jwt.JwtService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coop8.demojwt.Models.Departamento;
import com.coop8.demojwt.Models.Pais;
import com.coop8.demojwt.PayloadModels.DepartamentosResponseModel;
import com.coop8.demojwt.Repository.DepartamentosRepository;
import com.coop8.demojwt.Repository.PaisRepository;
import com.coop8.demojwt.Request.DepartamentosRequest;
import com.coop8.demojwt.Request.PaginacionRequest;
import com.coop8.demojwt.Request.RequestData;
import com.coop8.demojwt.Response.DepartamentosResponse;
import com.coop8.demojwt.Response.PaginacionResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;
import jakarta.validation.Valid;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class DepartamentosService {

    @Autowired
    DepartamentosRepository departamentosRepository;
    
    @Autowired
    PaisRepository paisRepository;
    
    @Autowired
    JwtService jwtService;

    public SecuredResponse getById(DepartamentosRequest departamentosRequest) throws Exception {
        log.info("DepartamentosService | getById");
        log.info("__departamentosRequest: " + Util.getJsonFromObject(departamentosRequest));

        DepartamentosResponse departamentosResponse = new DepartamentosResponse();

        Optional<Departamento> departamento = departamentosRepository.findById(departamentosRequest.getId());

        if (departamento.isPresent()) {
            List<Departamento> departamentosList = new ArrayList<>();
            departamentosList.add(departamento.get());

            List<DepartamentosResponseModel> departamentosResponseModels = new ArrayList<>();

            for (Departamento departamentos : departamentosList) {
                DepartamentosResponseModel eu = new DepartamentosResponseModel();
                eu.filterPayloadToSend(departamentos);
                departamentosResponseModels.add(eu);
            }

            departamentosResponse.setDepartamentos(departamentosResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(departamentosResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }

public ResponseEntity<?> list(DepartamentosRequest departamentosRequest) throws Exception {
    log.info("DepartamentosService | list");
    log.info("__departamentosRequest: " + Util.getJsonFromObject(departamentosRequest));

    List<Departamento> departamentosList;

    // Verificación y asignación predeterminada si paginacion es null
    int pagina = 0;
    int cantidad = 10; // Cantidad predeterminada de registros por página
    if (departamentosRequest.getPaginacion() != null) {
        pagina = Math.max(departamentosRequest.getPaginacion().getPagina() - 1, 0);
        cantidad = Math.max(departamentosRequest.getPaginacion().getCantidad(), 1); // Asegura que la cantidad sea al menos 1
    }
    Pageable paging = PageRequest.of(pagina, cantidad);

    Page<Departamento> pageDepartamentos;
    if (Util.isNullOrEmpty(departamentosRequest.getDescripcion())) {
        pageDepartamentos = departamentosRepository.findAllByOrderByDescripcionAsc(paging);
    } else {
        pageDepartamentos = departamentosRepository.findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(
                departamentosRequest.getDescripcion(), paging);
    }

    departamentosList = pageDepartamentos.getContent();

    List<DepartamentosResponseModel> departamentosResponseModels = new ArrayList<>();
    for (Departamento departamento : departamentosList) {
        DepartamentosResponseModel responseModel = new DepartamentosResponseModel();
        responseModel.filterPayloadToSend(departamento);
        departamentosResponseModels.add(responseModel);
    }

    DepartamentosResponse departamentosResponse = new DepartamentosResponse();
    departamentosResponse.setDepartamentos(departamentosResponseModels);
    PaginacionResponse paginacionResponse = new PaginacionResponse();
    paginacionResponse.setTotalItems(pageDepartamentos.getTotalElements());
    paginacionResponse.setTotalPages(pageDepartamentos.getTotalPages());
    paginacionResponse.setCurrentPages(pageDepartamentos.getNumber() + 1);
    departamentosResponse.setPaginacion(paginacionResponse);

    // Crear una nueva respuesta que contenga los datos de los departamentos
    return new ResponseEntity<>(departamentosResponse, HttpStatus.OK);
}


    @Transactional
    public SecuredResponse deleteById(DepartamentosRequest departamentosRequest) throws Exception {
        log.info("DepartamentosService | deleteById");
        log.info("__departamentosRequest: " + Util.getJsonFromObject(departamentosRequest));

        Integer idDepartamento = departamentosRequest.getId();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        departamentosRepository.deleteById(idDepartamento);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creacion del token con Claim
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    
    public SecuredResponse save(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
    log.info("DepartamentosService | save");
    log.info("__departamentosRequest: " + Util.getJsonFromObject(departamentosRequest));

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    // Obtenemos el usuario de SecurityContextHolder
    String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

    // Verificamos si es un UPDATE
    if (!Util.isNullOrEmpty(departamentosRequest.getId()) && departamentosRequest.getId() != 0) {
        // UPDATE
        Integer idDepartamento = departamentosRequest.getId();

        // Encuentra el departamento existente
        Optional<Departamento> optionalDepartamento = departamentosRepository.findById(idDepartamento);
        if (optionalDepartamento.isPresent()) {
            Departamento departamentoUpdate = optionalDepartamento.get();
            departamentoUpdate.setDescripcion(departamentosRequest.getDescripcion().toUpperCase());
            // Actualiza el campo usuariosys y fechaSistema si es necesario
            departamentoUpdate.setUsuariosys(usuariosys);
            departamentoUpdate.setFechasys(new Date()); // Asumiendo que fechaSistema es la fecha actual
            departamentosRepository.save(departamentoUpdate);

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
            // No se encontró el departamento para actualizar
            header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
            header.setTxtResultado("No se encontró el departamento con el ID proporcionado");
            response.setHeader(header);
            response.setData(null);

            // Creating the token with extra claims
            String token = jwtService.getDataFromPayload(response);
            securedResponse.setData(token);

            log.error("__response: " + Util.getJsonFromObject(response));
            log.error("__securedResponse: " + Util.getJsonFromObject(securedResponse));
            log.error("Token generado: " + token);
        }
    } else {
        // No se proporcionó un ID válido para la actualización
        header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
        header.setTxtResultado("No se proporcionó un ID válido para la actualización");
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


    /**
     * Método que crea un nuevo registro en la tabla departamentos
     * 
     * @param requestData
     * @return {SecuredResponse}
     * @throws Exception
     */
public SecuredResponse newAction(@Valid @RequestBody DepartamentosRequest departamentosRequest) throws Exception {
        log.info("DepartamentosService | newAction");
        log.info("__departamentosRequest: " + Util.getJsonFromObject(departamentosRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtener el usuario autenticado
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificar si es un INSERT
        if (Util.isNullOrEmpty(departamentosRequest.getId()) || departamentosRequest.getId() == 0) {
            // INSERT
            Departamento departamentoInsert = new Departamento();
            departamentoInsert.setDescripcion(departamentosRequest.getDescripcion().toUpperCase());

            // Validar que el ID del país no sea null
            if (departamentosRequest.getPais() == null) {
                throw new IllegalArgumentException("El ID del país no debe ser null");
            }

            // Obtener el país por ID y establecer la relación
            Optional<Pais> paisOpt = paisRepository.findById(departamentosRequest.getPais());
            if (paisOpt.isPresent()) {
                departamentoInsert.setPais(paisOpt.get());
            } else {
                throw new Exception("País no encontrado");
            }

            // Establecer el usuario autenticado al crear el nuevo registro
            departamentoInsert.setUsuariosys(usuariosys);
            departamentosRepository.save(departamentoInsert);

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


