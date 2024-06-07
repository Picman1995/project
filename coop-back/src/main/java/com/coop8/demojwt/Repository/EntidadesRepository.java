package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Entidades;

@Repository
public interface EntidadesRepository extends JpaRepository<Entidades, Integer> {

    
    Optional<Entidades> findById(int codEntidades);


    Page<Entidades> findAllByOrderByDescripcionAsc(Pageable pageable);

    Page<Entidades> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);

    void deleteById(int codEntidades);
}

