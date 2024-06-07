package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.CargoLaborales;



@Repository
public interface CargoLaboralesRespository  extends JpaRepository<CargoLaborales, Integer> {

    Optional<CargoLaborales> findById(Integer codCargoLaborales);

   

    void deleteById(Integer codCargoLaborales);

}
