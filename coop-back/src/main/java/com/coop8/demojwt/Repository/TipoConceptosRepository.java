package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoConceptos;



@Repository
public interface TipoConceptosRepository extends JpaRepository<TipoConceptos, Integer> {

    Optional<TipoConceptos> findById(Integer codTipoConceptos);

   

    void deleteById(Integer codTipoConceptos);




    
} 