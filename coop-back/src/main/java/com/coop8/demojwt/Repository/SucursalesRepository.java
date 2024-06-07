package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Sucursales;

@Repository
public interface SucursalesRepository extends JpaRepository<Sucursales, Integer> {


    Optional<Sucursales> findById(int codSucursales);


    Page<Sucursales> findAllByOrderByDescripcionAsc(Pageable pageable);


    Page<Sucursales> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);


    void deleteById(int codSucursales);
}

