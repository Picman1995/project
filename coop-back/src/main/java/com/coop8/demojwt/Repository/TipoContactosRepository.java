package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoContactos;

@Repository
public interface TipoContactosRepository extends JpaRepository<TipoContactos, Integer> {

    Optional<TipoContactos> findById(Integer codTipoContactos);

   

    void deleteById(Integer codTipoContactos);

}
