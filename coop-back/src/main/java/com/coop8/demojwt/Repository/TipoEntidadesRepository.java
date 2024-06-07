package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoEntidades;

@Repository
public interface TipoEntidadesRepository extends JpaRepository<TipoEntidades, Integer> {


    Optional<TipoEntidades> findById(int codTipoEntidades);


    Page<TipoEntidades> findAllByOrderByDescripcionAsc(Pageable pageable);

    Page<TipoEntidades> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);


    void deleteById(int codTipoEntidades);
}
