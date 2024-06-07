package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.CategoriaSocios;



@Repository
public interface CategoriaSociosRepository extends JpaRepository<CategoriaSocios, Integer> {

    Optional<CategoriaSocios> findById(Integer codCategoriaSocios);   

  
    boolean existsByDescripcion(String descripcion);

    void deleteById(Integer codCategoriaSocios);

}
