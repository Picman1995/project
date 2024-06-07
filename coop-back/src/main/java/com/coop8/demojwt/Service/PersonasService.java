package com.coop8.demojwt.Service;

import com.coop8.demojwt.Jwt.JwtService;
import com.coop8.demojwt.Models.EstadoCiviles;
import com.coop8.demojwt.Models.Ciudad;
import com.coop8.demojwt.Models.Nacionalidades;
import com.coop8.demojwt.Models.Personas;
import com.coop8.demojwt.Models.TipoDocumentos;
import com.coop8.demojwt.Models.TipoPersonas;
import com.coop8.demojwt.PayloadModels.PersonasResponseModel;
import com.coop8.demojwt.Repository.CiudadRepository;
import com.coop8.demojwt.Repository.EstadoCivilesRepository;
import com.coop8.demojwt.Repository.NacionalidadesRepository;
import com.coop8.demojwt.Repository.PersonasRepository;
import com.coop8.demojwt.Repository.TipoDocumentosRepository;
import com.coop8.demojwt.Repository.TipoPersonasRepository;
import com.coop8.demojwt.Request.PaginacionRequest;
import com.coop8.demojwt.Request.PersonasRequest;
import com.coop8.demojwt.Response.PaginacionResponse;
import com.coop8.demojwt.Response.PersonasResponse;
import com.coop8.demojwt.Response.Response;
import com.coop8.demojwt.Response.ResponseHeader;
import com.coop8.demojwt.Response.SecuredResponse;
import com.coop8.demojwt.Utils.DateUtil;
import com.coop8.demojwt.Utils.ECodigosRespuestas;
import com.coop8.demojwt.Utils.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class PersonasService {
    
    @Autowired
    PersonasRepository personasRepository;
    
    @Autowired
    CiudadRepository ciudadRepository;
    
    @Autowired
    TipoDocumentosRepository tipoDocumentosRepository;
    
    @Autowired
    TipoPersonasRepository tipoPersonasRepository;
    
    @Autowired
    EstadoCivilesRepository estadoCivilesRepository;
    
    @Autowired
    NacionalidadesRepository nacionalidadesRepository;
    
    @Autowired
    JwtService jwtService;
    
public SecuredResponse save(@Valid @RequestBody PersonasRequest personasRequest) {
    log.info("PersonaService | save");
    log.info("__personasRequest: " + Util.getJsonFromObject(personasRequest));

    // Agrega un mensaje de registro para verificar que se está ejecutando el método save correctamente
    log.info("Intentando guardar o actualizar la persona en la base de datos...");

    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    try {
        // Obtener el usuario autenticado
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificar si se proporcionó un ID para determinar si es una inserción o una actualización
        if (personasRequest.getId() != null && personasRequest.getId() > 0) {
            // Actualización
            log.info("Intentando actualizar la persona con ID: " + personasRequest.getId());

            Optional<Personas> personaOpt = personasRepository.findById(personasRequest.getId());
            if (personaOpt.isPresent()) {
                Personas personaUpdate = personaOpt.get();
                // Actualizar los campos de la persona
                personaUpdate.setNroDocumento(personasRequest.getNroDocumento());
                personaUpdate.setNombre1(personasRequest.getNombre1());
                personaUpdate.setNombre2(personasRequest.getNombre2());
                personaUpdate.setNombre3(personasRequest.getNombre3());
                personaUpdate.setApellido1(personasRequest.getApellido1());
                personaUpdate.setApellido2(personasRequest.getApellido2());
                personaUpdate.setApellido3(personasRequest.getApellido3());
                personaUpdate.setFechaNacimiento(personasRequest.getFechaNacimiento() != null ? new Date(personasRequest.getFechaNacimiento().getTime()) : null);
                personaUpdate.setSexo(personasRequest.getSexo());

                // Establecer las relaciones por ID
                if (personasRequest.getCiudad() != null) {
                    Optional<Ciudad> ciudadOpt = ciudadRepository.findById(personasRequest.getCiudad());
                    ciudadOpt.ifPresent(ciudad -> personaUpdate.setCiudad(ciudad));
                }

                if (personasRequest.getTipoDocumento() != null) {
                    Optional<TipoDocumentos> tipoDocumentoOpt = tipoDocumentosRepository.findById(personasRequest.getTipoDocumento());
                    tipoDocumentoOpt.ifPresent(tipoDocumento -> personaUpdate.setTipoDocumento(tipoDocumento));
                }

                if (personasRequest.getTipoPersona() != null) {
                    Optional<TipoPersonas> tipoPersonaOpt = tipoPersonasRepository.findById(personasRequest.getTipoPersona());
                    tipoPersonaOpt.ifPresent(tipoPersona -> personaUpdate.setTipoPersona(tipoPersona));
                }

                if (personasRequest.getEstadoCivil() != null) {
                    Optional<EstadoCiviles> estadoCivilOpt = estadoCivilesRepository.findById(personasRequest.getEstadoCivil());
                    estadoCivilOpt.ifPresent(estadoCivil -> personaUpdate.setEstadoCivil(estadoCivil));
                }

                if (personasRequest.getNacionalidad() != null) {
                    Optional<Nacionalidades> nacionalidadOpt = nacionalidadesRepository.findById(personasRequest.getNacionalidad());
                    nacionalidadOpt.ifPresent(nacionalidad -> personaUpdate.setNacionalidad(nacionalidad));
                }

                // Establecer el usuario autenticado al actualizar el registro
                personaUpdate.setUsuariosys(usuariosys);

                // Guardar la persona actualizada en la base de datos
                personasRepository.save(personaUpdate);

                header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
                header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
                response.setHeader(header);
                response.setData(null);

                // Crear el token con los datos de la persona actualizada
                String token = jwtService.getDataFromPayload(response);
                securedResponse.setData(token);
            } else {
                throw new Exception("Persona no encontrada para actualizar");
            }
        } else {
            throw new Exception("ID de persona no proporcionado para la actualización");
        }
    } catch (Exception e) {
        // Capturar cualquier excepción y enviar una respuesta de error al cliente
        header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
        header.setTxtResultado(e.getMessage()); // Puedes personalizar el mensaje de error aquí
        response.setHeader(header);
        response.setData(null);
        String token = jwtService.getDataFromPayload(response);
        securedResponse.setData(token);
    }

    return securedResponse;
}



    public SecuredResponse newAction(@Valid @RequestBody PersonasRequest personasRequest) throws Exception {
        log.info("PersonaService | newAction");
        log.info("__personasRequest: " + Util.getJsonFromObject(personasRequest));

        ResponseHeader header = new ResponseHeader();
        Response response = new Response();
        SecuredResponse securedResponse = new SecuredResponse();

        // Obtener el usuario autenticado
        String usuariosys = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificar si es un INSERT
        if (personasRequest.getId() == null || personasRequest.getId() == 0) {
            // INSERT
            Personas personaInsert = new Personas();
            // Establecer los campos de persona
            personaInsert.setNroDocumento(personasRequest.getNroDocumento());
            personaInsert.setNombre1(personasRequest.getNombre1());
            personaInsert.setNombre2(personasRequest.getNombre2());
            personaInsert.setNombre3(personasRequest.getNombre3());
            personaInsert.setApellido1(personasRequest.getApellido1());
            personaInsert.setApellido2(personasRequest.getApellido2());
            personaInsert.setApellido3(personasRequest.getApellido3());
            personaInsert.setFechaNacimiento(personasRequest.getFechaNacimiento() != null ? new Date(personasRequest.getFechaNacimiento().getTime()) : null);
            personaInsert.setSexo(personasRequest.getSexo());

            // Establecer las relaciones por ID
            if (personasRequest.getCiudad() != null) {
                Optional<Ciudad> ciudadOpt = ciudadRepository.findById(personasRequest.getCiudad());
                if (ciudadOpt.isPresent()) {
                    personaInsert.setCiudad(ciudadOpt.get());
                } else {
                    throw new Exception("Ciudad no encontrada");
                }
            }

            if (personasRequest.getTipoDocumento() != null) {
                Optional<TipoDocumentos> tipoDocumentoOpt = tipoDocumentosRepository.findById(personasRequest.getTipoDocumento());
                if (tipoDocumentoOpt.isPresent()) {
                    personaInsert.setTipoDocumento(tipoDocumentoOpt.get());
                } else {
                    throw new Exception("Tipo de documento no encontrado");
                }
            }

            if (personasRequest.getTipoPersona() != null) {
                Optional<TipoPersonas> tipoPersonaOpt = tipoPersonasRepository.findById(personasRequest.getTipoPersona());
                if (tipoPersonaOpt.isPresent()) {
                    personaInsert.setTipoPersona(tipoPersonaOpt.get());
                } else {
                    throw new Exception("Tipo de persona no encontrado");
                }
            }

            if (personasRequest.getEstadoCivil() != null) {
                Optional<EstadoCiviles> estadoCivilOpt = estadoCivilesRepository.findById(personasRequest.getEstadoCivil());
                if (estadoCivilOpt.isPresent()) {
                    personaInsert.setEstadoCivil(estadoCivilOpt.get());
                } else {
                    throw new Exception("Estado civil no encontrado");
                }
            }

            if (personasRequest.getNacionalidad() != null) {
                Optional<Nacionalidades> nacionalidadOpt = nacionalidadesRepository.findById(personasRequest.getNacionalidad());
                if (nacionalidadOpt.isPresent()) {
                    personaInsert.setNacionalidad(nacionalidadOpt.get());
                } else {
                    throw new Exception("Nacionalidad no encontrada");
                }
            }

            // Establecer el usuario autenticado al crear el nuevo registro
            personaInsert.setUsuariosys(usuariosys);

            // Guardar la nueva persona en la base de datos
            personasRepository.save(personaInsert);

            header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
            header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
            response.setHeader(header);
            response.setData(null);

            // Crear el token con los datos de la persona insertada
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



 public ResponseEntity<?> list(PersonasRequest personasRequest) {
    log.info("PersonasService | list");
    log.info("__personasRequest: " + Util.getJsonFromObject(personasRequest));

    int pagina = 0;
    int cantidad = 1; // Cantidad predeterminada de registros por página
    if (personasRequest.getPaginacion() != null) {
        pagina = Math.max(personasRequest.getPaginacion().getPagina() - 1, 0);
        cantidad = Math.max(personasRequest.getPaginacion().getCantidad(), 1); // Asegura que la cantidad sea al menos 1
    }
    Pageable paging = PageRequest.of(pagina, cantidad);

    Page<Personas> pagePersonas;
    if (personasRequest.getId() != null) {
        // Si se proporciona un ID, se buscará solo esa persona
        Optional<Personas> optionalPersona = personasRepository.findById(personasRequest.getId());
        if (optionalPersona.isPresent()) {
            List<Personas> personasList = Collections.singletonList(optionalPersona.get());
            pagePersonas = new PageImpl<>(personasList, paging, personasList.size());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada con ID: " + personasRequest.getId());
        }
    } else {
        // Si no se proporciona un ID, se buscarán todas las personas
        pagePersonas = personasRepository.findAll(paging);
    }

    // Mapea los resultados a la respuesta deseada
    List<PersonasResponseModel> personasResponseModels = pagePersonas.map(persona -> {
        PersonasResponseModel model = new PersonasResponseModel();
        model.filterPayloadToSend(persona);
        return model;
    }).getContent();

    PersonasResponse personasResponse = new PersonasResponse();
    personasResponse.setPersonas(personasResponseModels);

    PaginacionResponse paginacionResponse = new PaginacionResponse();
    paginacionResponse.setTotalItems(pagePersonas.getTotalElements());
    paginacionResponse.setTotalPages(pagePersonas.getTotalPages());
    paginacionResponse.setCurrentPages(pagePersonas.getNumber() + 1);
    personasResponse.setPaginacion(paginacionResponse);

    return new ResponseEntity<>(personasResponse, HttpStatus.OK);
}


public ResponseEntity<?> getById(@Valid PersonasRequest personasRequest) throws Exception {
    log.info("PersonasService | getById");
    log.info("__personasRequest: " + Util.getJsonFromObject(personasRequest));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
        throw new Exception("Usuario no autenticado");
    }

    Object principal = authentication.getPrincipal();
    UserDetails userDetails;

    if (principal instanceof UserDetails) {
        userDetails = (UserDetails) principal;
    } else if (principal instanceof String) {
        String username = (String) principal;
    } else {
        throw new ClassCastException("El principal de autenticación no es una instancia de UserDetails ni un String");
    }

    PersonasResponse personasResponse = new PersonasResponse();
    String nroDocumento = personasRequest.getNroDocumento();
    Optional<Personas> persona = personasRepository.findByNroDocumento(nroDocumento);

    if (persona.isPresent()) {
        List<PersonasResponseModel> personasResponseModels = new ArrayList<>();
        PersonasResponseModel eu = new PersonasResponseModel();
        eu.filterPayloadToSend(persona.get());
        personasResponseModels.add(eu);

        personasResponse.setPersonas(personasResponseModels);

        PaginacionResponse pageableResponse = new PaginacionResponse();
        pageableResponse.setTotalItems(1);
        pageableResponse.setTotalPages(1);
        pageableResponse.setCurrentPages(1);
        personasResponse.setPaginacion(pageableResponse);

        log.info("__PersonasResponse: " + Util.getJsonFromObject(personasResponse));
        return new ResponseEntity<>(personasResponse, HttpStatus.OK);
    } else {
        log.error("Persona no encontrada con el número de cédula: " + nroDocumento);
        return new ResponseEntity<>("Persona no encontrada", HttpStatus.NOT_FOUND);
    }
}

    

public SecuredResponse deleteById(@Valid PersonasRequest personasRequest) throws Exception {
    log.info("PersonasService | deleteById");
    log.info("__personasRequest: " + Util.getJsonFromObject(personasRequest));

    Integer idPersona = personasRequest.getId();
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    personasRepository.deleteById(idPersona);
    header.setCodResultado(ECodigosRespuestas.SUCCESS.getCodigoRespuesta());
    header.setTxtResultado(ECodigosRespuestas.SUCCESS.getTxtRespuesta());
    response.setHeader(header);
    response.setData(null);

    securedResponse.setData(jwtService.getDataFromPayload(response));
    log.info("__response: " + Util.getJsonFromObject(response));
    log.info("__securedResponse: " + Util.getJsonFromObject(securedResponse));

    return securedResponse;
}


private SecuredResponse handleMissingToken() {
    ResponseHeader header = new ResponseHeader();
    Response response = new Response();
    SecuredResponse securedResponse = new SecuredResponse();

    header.setCodResultado(ECodigosRespuestas.ERROR.getCodigoRespuesta());
    header.setTxtResultado("JWT String argument cannot be null or empty.");
    response.setHeader(header);
    response.setData(null);

    try {
        securedResponse.setData(jwtService.getDataFromPayload(response));
    } catch (Exception ex) {
        log.error("Error handling missing token", ex);
    }
    return securedResponse;
}


}