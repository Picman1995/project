package com.coop8.demojwt.Request;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonasRequest implements Serializable {

    private static final long serialVersionUID = -5858040595024021418L;

    private Integer id; 
    private String nroDocumento; 
    private String nombre1;
    private String nombre2;    
    private String nombre3;
    private String apellido1;    
    private String apellido2;
    private String apellido3;
    private Date fechaNacimiento;    
    private String sexo;
    private Integer tipoDocumento; 
    private Integer tipoPersona;           
    private Integer estadoCivil; 
    private Integer ciudad; 
    private Integer nacionalidad; 

    private PaginacionRequest paginacion; // Este campo puede ser opcional para la creación/actualización de personas.
}
