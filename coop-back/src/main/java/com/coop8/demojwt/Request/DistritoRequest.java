package com.coop8.demojwt.Request;

import com.coop8.demojwt.Models.Departamento;
import com.coop8.demojwt.Models.Distrito;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author picman
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistritoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer codigoSet;
    private Integer codigoSicoop;
    private Integer departamento;
    private String descripcion;
    private PaginacionRequest paginacion;

    
}
