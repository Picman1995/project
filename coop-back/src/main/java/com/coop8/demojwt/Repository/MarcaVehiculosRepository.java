package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.MarcaVehiculos;





@Repository
public interface MarcaVehiculosRepository extends JpaRepository<MarcaVehiculos, Integer> {


    Optional<MarcaVehiculos> findById(Integer codMarcaVehiculos);

   

    void deleteById(Integer codMarcaVehiculos);


    
} 