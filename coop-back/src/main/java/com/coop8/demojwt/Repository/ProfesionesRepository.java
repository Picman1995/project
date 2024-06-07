package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Profesiones;



@Repository
public interface ProfesionesRepository  extends JpaRepository<Profesiones, Integer> {

    Optional<Profesiones> findById(Integer codProfesiones);

   

    void deleteById(Integer codProfesiones);
    
} 