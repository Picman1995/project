package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Personas;


@Repository
public interface PersonasRepository extends JpaRepository<Personas, Integer> {

	    // Método personalizado para buscar por ID
    Optional<Personas> findById(Integer id);
    
        // Método personalizado para buscar por número de cédula
    Optional<Personas> findByNroDocumento(String nroDocumento);
    
    default Personas saveOrUpdate(Personas persona) {
        return save(persona);
    }
}