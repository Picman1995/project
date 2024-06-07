package com.coop8.demojwt.Service;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Ciudad;
import com.coop8.demojwt.Models.Distrito;
import com.coop8.demojwt.PayloadModels.CiudadResponseModel;
import com.coop8.demojwt.Repository.CiudadRepository;
import com.coop8.demojwt.Repository.DistritoRepository;
import com.coop8.demojwt.Request.CiudadRequest;
import com.coop8.demojwt.Response.CiudadResponse;
import com.coop8.demojwt.Response.PaginacionResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author picman
 */
@Service
@Slf4j
public class CiudadService {

    @Autowired
    CiudadRepository ciudadRepository;
    
    @Autowired
    DistritoRepository distritoRepository;

    @Autowired
    JwtService jwtService;

    
    public ResponseEntity<?> list(CiudadRequest ciudadRequest) {
        log.info("CiudadService | list");
        log.info("__ciudadRequest: " + Util.getJsonFromObject(ciudadRequest));

        int pagina = 0;
        int cantidad = 10; // Cantidad predeterminada de registros por página
        if (ciudadRequest.getPaginacion() != null) {
            pagina = Math.max(ciudadRequest.getPaginacion().getPagina() - 1, 0);
            cantidad = Math.max(ciudadRequest.getPaginacion().getCantidad(), 1); // Asegura que la cantidad sea al menos 1
        }
        Pageable paging = PageRequest.of(pagina, cantidad);

        Page<Ciudad> pageCiudades;
        if (Util.isNullOrEmpty(ciudadRequest.getDescripcion())) {
            pageCiudades = ciudadRepository.findAllByOrderByDescripcionAsc(paging);
        } else {
            pageCiudades = ciudadRepository.findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(
                    ciudadRequest.getDescripcion(), paging);
        }

        List<CiudadResponseModel> ciudadResponseModels = pageCiudades.map(ciudad -> {
            CiudadResponseModel model = new CiudadResponseModel();
            model.filterPayloadToSend(ciudad);
            return model;
        }).getContent();

        CiudadResponse ciudadResponse = new CiudadResponse();
        ciudadResponse.setCiudades(ciudadResponseModels);
        
        PaginacionResponse paginacionResponse = new PaginacionResponse();
        paginacionResponse.setTotalItems(pageCiudades.getTotalElements());
        paginacionResponse.setTotalPages(pageCiudades.getTotalPages());
        paginacionResponse.setCurrentPages(pageCiudades.getNumber() + 1);
        ciudadResponse.setPaginacion(paginacionResponse);

        return new ResponseEntity<>(ciudadResponse, HttpStatus.OK);
    }
    
    public SecuredResponse getById(CiudadRequest ciudadRequest) {
        log.info("CiudadService | getById");
        log.info("__ciudadRequest: " + Util.getJsonFromObject(ciudadRequest));

        CiudadResponse ciudadResponse = new CiudadResponse();

        Optional<Ciudad> ciudad = ciudadRepository.findById(ciudadRequest.getId());

        if (ciudad.isPresent()) {
            List<Ciudad> ciudadList = new ArrayList<>();
            ciudadList.add(ciudad.get());

            List<CiudadResponseModel> ciudadResponseModels = new ArrayList<>();

            for (Ciudad ciudades : ciudadList) {
                CiudadResponseModel model = new CiudadResponseModel();
                model.filterPayloadToSend(ciudades);
                ciudadResponseModels.add(model);
            }

            ciudadResponse.setCiudades(ciudadResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(ciudadResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
    
    public SecuredResponse save(CiudadRequest ciudadRequest) throws Exception {
        log.info("CiudadService | save");
        log.info("__ciudadRequest: " + Util.getJsonFromObject(ciudadRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtenemos el usuario autenticado
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos si se proporcionó un ID válido para la actualización
        if (ciudadRequest.getId() != null && ciudadRequest.getId() > 0) {
            // UPDATE
            // Encuentra la ciudad existente
            Optional<Ciudad> optionalCiudad = ciudadRepository.findById(ciudadRequest.getId());
            if (optionalCiudad.isPresent()) {
                Ciudad ciudadUpdate = optionalCiudad.get();
                // Actualiza los campos de la ciudad
                ciudadUpdate.setCodigoSet(ciudadRequest.getCodigoSet());
                ciudadUpdate.setCodigoSicoop(ciudadRequest.getCodigoSicoop());
                ciudadUpdate.setDescripcion(ciudadRequest.getDescripcion());
                // Establece el usuario autenticado
                ciudadUpdate.setUsuariosys(usuariosys);
                // Guarda los cambios en la base de datos
                ciudadRepository.save(ciudadUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                // Crear el token con los datos de la ciudad actualizada
                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);
            } else {
                // No se encontró la ciudad para actualizar
                header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
                header.setTxtResultado("No se encontró la ciudad con el ID proporcionado");
                response.setHeader(header);
                response.setData(null);
                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);
            }
        } else {
            // No se proporcionó un ID válido para la actualización
            header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
            header.setTxtResultado("No se proporcionó un ID válido para la actualización");
            response.setHeader(header);
            response.setData(null);
            String token = jwtService.getDataFromPayload(response);
            securedResponse.setData(token);
        }

        return securedResponse;
    }

public SecuredResponse newAction(@Valid @RequestBody CiudadRequest ciudadRequest) throws Exception {
    log.info("CiudadService | newAction");
    log.info("__ciudadRequest: " + Util.getJsonFromObject(ciudadRequest));

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    // Obtener el usuario autenticado
    String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

    // Verificar si es un INSERT
    if (ciudadRequest.getId() == null || ciudadRequest.getId() == 0) {
        // INSERT
        Ciudad ciudadInsert = new Ciudad();
        // Establecer los campos del distrito
        ciudadInsert.setCodigoSet(ciudadRequest.getCodigoSet());
        ciudadInsert.setCodigoSicoop(ciudadRequest.getCodigoSicoop());
        ciudadInsert.setDescripcion(ciudadRequest.getDescripcion());
        
        // Establecer el departamento por ID
        Optional<Distrito> distritoOpt = distritoRepository.findById(ciudadRequest.getDistrito());
        if (distritoOpt.isPresent()) {
            ciudadInsert.setDistrito(distritoOpt.get());
        } else {
            throw new Exception("Departamento no encontrado");
        }
        
        // Establecer el usuario autenticado al crear el nuevo registro
        ciudadInsert.setUsuariosys(usuariosys);

        // Guardar el nuevo distrito en la base de datos
        ciudadRepository.save(ciudadInsert);

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Crear el token con los datos del distrito insertado
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

    @Transactional
    public SecuredResponse deleteById(CiudadRequest ciudadRequest) {
        log.info("CiudadService | deleteById");
        log.info("__ciudadRequest: " + Util.getJsonFromObject(ciudadRequest));

        Integer idCiudad = ciudadRequest.getId();
        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        ciudadRepository.deleteById(idCiudad);
        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(null);

        // Creación del token con Claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
}