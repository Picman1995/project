package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoSolicitudes;



@Repository
public interface TipoSolicitudesRepository  extends JpaRepository<TipoSolicitudes, Integer> {

    Optional<TipoSolicitudes> findById(Integer codTipoSolicitudes);  

    void deleteById(Integer codTipoSolicitudes);

    
} 