package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Distrito;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {

    /**
     * Busca y obtiene un Distrito en base al campo id
     * 
     * @param id
     * @return Optional<Distrito>
     */
    Optional<Distrito> findById(Integer id);

    /**
     * Busca y obtiene una página de Distritos ordenados por descripción
     * 
     * @param pageable
     * @return Page<Distrito>
     */
    Page<Distrito> findAllByOrderByDescripcionAsc(Pageable pageable);

    /**
     * Busca y obtiene una página de Distritos que contienen una descripción específica
     * 
     * @param descripcion
     * @param pageable
     * @return Page<Distrito>
     */
    Page<Distrito> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion, Pageable pageable);

    /**
     * Elimina un registro en base al campo id
     * 
     * @param id
     */
    void deleteById(Integer id);

    // Puedes agregar métodos adicionales aquí según sea necesario
}
