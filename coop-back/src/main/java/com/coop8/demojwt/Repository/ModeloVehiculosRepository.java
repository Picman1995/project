package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.ModeloVehiculos;

@Repository
public interface ModeloVehiculosRepository extends JpaRepository<ModeloVehiculos, Integer> {

    
    Optional<ModeloVehiculos> findById(int codModeloVehiculos);

    
    Page<ModeloVehiculos> findAllByOrderByDescripcionAsc(Pageable pageable);

    
    Page<ModeloVehiculos> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);

   
    void deleteById(int codModeloVehiculos);
}

