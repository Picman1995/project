/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coop8.demojwt.Repository;

import com.coop8.demojwt.Models.Pais;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author picman
 */
@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    // Encuentra un país por su descripción
    Optional<Pais> findByDescripcion(String descripcion);

    // Verifica si existe un país con una descripción específica
    boolean existsByDescripcion(String descripcion);

    // Elimina un país por su identificador
    void deleteById(Integer codPais);
}