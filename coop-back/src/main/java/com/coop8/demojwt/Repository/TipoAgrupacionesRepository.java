package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoAgrupaciones;

@Repository
public interface TipoAgrupacionesRepository extends JpaRepository<TipoAgrupaciones, Integer> {

    /**
     * Busca y obtiene un {OptionalTipoAgrupaciones} en base al campo id
     * 
     * @param id
     * @return OptionalTipoAgrupaciones
     */
    Optional<TipoAgrupaciones> findById(Integer codTipoAgrupaciones);

    /**
     * Busca y obtiene un {PageTipoAgrupaciones} de todos los tipos de entidades
     * 
     * @param pageable
     * @return PageTipoAgrupaciones
     */
    Page<TipoAgrupaciones> findAllByOrderByDescripcionAsc(Pageable pageable);

    /**
     * Busca y obtiene un {PageTipoAgrupaciones} en base al campo descripcion
     * 
     * @param descripcion
     * @param pageable
     * @return Page<TipoAgrupaciones
     */
    Page<TipoAgrupaciones> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);

    /**
     * Elimina un registro en base al campo id
     * 
     * @param id
     */
    void deleteById(Integer codTipoAgrupaciones);
}

