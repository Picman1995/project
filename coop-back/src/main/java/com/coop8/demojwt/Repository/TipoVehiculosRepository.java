package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoVehiculos;

@Repository
public interface TipoVehiculosRepository extends JpaRepository<TipoVehiculos, Integer> {

    Optional<TipoVehiculos> findById(int codTipoVehiculos);


    Page<TipoVehiculos> findAllByOrderByDescripcionAsc(Pageable pageable);


    Page<TipoVehiculos> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);


    void deleteById(int codTipoVehiculos);
}
