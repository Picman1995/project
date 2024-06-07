package com.coop8.demojwt.PayloadModels;

import com.coop8.demojwt.Models.Personas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonasResponseModel {
    private Integer id;
    private String nroDocumento;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private String apellido3;
    private String fechaNacimiento;
    private String sexo;
    private String tipoDocumento;
    private Integer idTipoDocumento;
    private String tipoPersona;
    private Integer idTipoPersona;
    private String estadoCivil;
    private Integer idEstadoCivil;
    private String ciudad;  
    private Integer idCiudad;
    private String nacionalidad; 
    private Integer idNacionalidad;

    public void filterPayloadToSend(Personas personas) {
        this.id = personas.getId();
        this.nroDocumento = personas.getNroDocumento();
        this.nombre1 = personas.getNombre1();
        this.nombre2 = personas.getNombre2();
        this.nombre3 = personas.getNombre3();
        this.apellido1 = personas.getApellido1();
        this.apellido2 = personas.getApellido2();
        this.apellido3 = personas.getApellido3();
        this.fechaNacimiento = personas.getFechaNacimiento() != null ? personas.getFechaNacimiento().toString() : null;
        this.sexo = personas.getSexo();
        this.tipoDocumento = personas.getTipoDocumento() != null ? personas.getTipoDocumento().getDescripcion() : null;
        this.idTipoDocumento = personas.getTipoDocumento() != null ? personas.getTipoDocumento().getCodTipoDocumentos() : null; // Nuevo campo
        this.tipoPersona = personas.getTipoPersona() != null ? personas.getTipoPersona().getDescripcion() : null;
        this.idTipoPersona = personas.getTipoPersona() != null ? personas.getTipoPersona().getCodTipoPersonas() : null; // Nuevo campo
        this.estadoCivil = personas.getEstadoCivil() != null ? personas.getEstadoCivil().getDescripcion() : null;
        this.idEstadoCivil = personas.getEstadoCivil() != null ? personas.getEstadoCivil().getCodEstadoCiviles() : null; // Nuevo campo
        this.ciudad = personas.getCiudad() != null ? personas.getCiudad().getDescripcion() : null;
        this.idCiudad = personas.getCiudad() != null ? personas.getCiudad().getCodCiudad() : null;
        this.nacionalidad = personas.getNacionalidad() != null ? personas.getNacionalidad().getDescripcion() : null;
        this.idNacionalidad = personas.getNacionalidad() != null ? personas.getNacionalidad().getId() : null; // Nuevo campo
    }
}