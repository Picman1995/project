package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Conceptos;



@Repository
public interface ConceptosRepository extends JpaRepository<Conceptos, Integer>{
     Optional<Conceptos> findById(Long id);

   

    void deleteById(Long id);

    
} 