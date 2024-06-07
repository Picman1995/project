package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.ActividadEconomicas;



@Repository
public interface ActividadEconomicasRepository extends JpaRepository<ActividadEconomicas, Integer> {


    Optional<ActividadEconomicas> findById(Integer codActividadEconomicas);

   

    void deleteById(Integer codActividadEconomicas);


}
