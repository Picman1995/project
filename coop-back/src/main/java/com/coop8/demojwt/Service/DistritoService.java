package com.coop8.demojwt.Service;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.Departamento;
import com.coop8.demojwt.Models.Distrito;
import com.coop8.demojwt.PayloadModels.DistritoResponseModel;
import com.coop8.demojwt.Repository.DepartamentosRepository;
import com.coop8.demojwt.Repository.DistritoRepository;
import com.coop8.demojwt.Request.DistritoRequest;
import com.coop8.demojwt.Response.DistritoResponse;
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
public class DistritoService {

    @Autowired
    DistritoRepository distritoRepository;
    
    @Autowired
    DepartamentosRepository departamentosRepository;

    @Autowired
    JwtService jwtService;

    
  public ResponseEntity<?> list(DistritoRequest distritoRequest) {
    log.info("DistritoService | list");
    log.info("__distritoRequest: " + Util.getJsonFromObject(distritoRequest));

    int pagina = 0;
    int cantidad = 10; // Cantidad predeterminada de registros por página
    if (distritoRequest.getPaginacion() != null) {
        pagina = Math.max(distritoRequest.getPaginacion().getPagina() - 1, 0);
        cantidad = Math.max(distritoRequest.getPaginacion().getCantidad(), 1); // Asegura que la cantidad sea al menos 1
    }
    Pageable paging = PageRequest.of(pagina, cantidad);

    Page<Distrito> pageDistritos;
    if (Util.isNullOrEmpty(distritoRequest.getDescripcion())) {
        pageDistritos = distritoRepository.findAllByOrderByDescripcionAsc(paging);
    } else {
        pageDistritos = distritoRepository.findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(
                distritoRequest.getDescripcion(), paging);
    }

    List<DistritoResponseModel> distritoResponseModels = pageDistritos.map(distrito -> {
        DistritoResponseModel model = new DistritoResponseModel();
        model.filterPayloadToSend(distrito);
        return model;
    }).getContent();

    DistritoResponse distritoResponse = new DistritoResponse();
    distritoResponse.setDistritos(distritoResponseModels);
    
    PaginacionResponse paginacionResponse = new PaginacionResponse();
    paginacionResponse.setTotalItems(pageDistritos.getTotalElements());
    paginacionResponse.setTotalPages(pageDistritos.getTotalPages());
    paginacionResponse.setCurrentPages(pageDistritos.getNumber() + 1);
    distritoResponse.setPaginacion(paginacionResponse);

    return new ResponseEntity<>(distritoResponse, HttpStatus.OK);
}
    
        public SecuredResponse getById(DistritoRequest distritoRequest) {
        log.info("DistritoService | getById");
        log.info("__distritoRequest: " + Util.getJsonFromObject(distritoRequest));

        DistritoResponse distritoResponse = new DistritoResponse();

        Optional<Distrito> distrito = distritoRepository.findById(distritoRequest.getId());

        if (distrito.isPresent()) {
            List<Distrito> distritoList = new ArrayList<>();
            distritoList.add(distrito.get());

            List<DistritoResponseModel> distritoResponseModels = new ArrayList<>();

            for (Distrito distritos : distritoList) {
                DistritoResponseModel model = new DistritoResponseModel();
                model.filterPayloadToSend(distritos);
                distritoResponseModels.add(model);
            }

            distritoResponse.setDistritos(distritoResponseModels);
        }

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
        header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
        response.setHeader(header);
        response.setData(distritoResponse);

        // Creating the token with extra claims
        String token = Util.getJsonFromObject(response);
        securedResponse.setData(token);

        return securedResponse;
    }
        
public SecuredResponse save(DistritoRequest distritoRequest) throws Exception {
    log.info("DistritoService | save");
    log.info("__distritoRequest: " + Util.getJsonFromObject(distritoRequest));

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    // Obtenemos el usuario autenticado
    String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

    // Verificamos si se proporcionó un ID válido para la actualización
    if (distritoRequest.getId() != null && distritoRequest.getId() > 0) {
        // UPDATE
        // Encuentra el distrito existente
        Optional<Distrito> optionalDistrito = distritoRepository.findById(distritoRequest.getId());
        if (optionalDistrito.isPresent()) {
            Distrito distritoUpdate = optionalDistrito.get();
            // Actualiza solo la descripción del distrito
            distritoUpdate.setDescripcion(distritoRequest.getDescripcion());
            // Establece el usuario autenticado
            distritoUpdate.setUsuariosys(usuariosys);
            // Guarda los cambios en la base de datos
            distritoRepository.save(distritoUpdate);

            header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
            header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
            response.setHeader(header);
            response.setData(null);

            // Crear el token con los datos del distrito actualizado
            String token = jwtService.getDataFromPayload(response);
            securedResponse.setData(token);
        } else {
            // No se encontró el distrito para actualizar
            header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
            header.setTxtResultado("No se encontró el distrito con el ID proporcionado");
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


public SecuredResponse newAction(@Valid @RequestBody DistritoRequest distritoRequest) throws Exception {
    log.info("DistritoService | newAction");
    log.info("__distritoRequest: " + Util.getJsonFromObject(distritoRequest));

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    // Obtener el usuario autenticado
    String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

    // Verificar si es un INSERT
    if (distritoRequest.getId() == null || distritoRequest.getId() == 0) {
        // INSERT
        Distrito distritoInsert = new Distrito();
        // Establecer los campos del distrito
        distritoInsert.setCodigoSet(distritoRequest.getCodigoSet());
        distritoInsert.setCodigoSicoop(distritoRequest.getCodigoSicoop());
        distritoInsert.setDescripcion(distritoRequest.getDescripcion());
        
        // Establecer el departamento por ID
        Optional<Departamento> departamentoOpt = departamentosRepository.findById(distritoRequest.getDepartamento());
        if (departamentoOpt.isPresent()) {
            distritoInsert.setDepartamento(departamentoOpt.get());
        } else {
            throw new Exception("Departamento no encontrado");
        }
        
        // Establecer el usuario autenticado al crear el nuevo registro
        distritoInsert.setUsuariosys(usuariosys);

        // Guardar el nuevo distrito en la base de datos
        distritoRepository.save(distritoInsert);

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
public SecuredResponse deleteById(DistritoRequest distritoRequest) {
    log.info("DistritoService | deleteById");
    log.info("__distritoRequest: " + Util.getJsonFromObject(distritoRequest));

    Integer idDistrito = distritoRequest.getId();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    distritoRepository.deleteById(idDistrito);
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