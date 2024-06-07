package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoPersonas;


@Repository
public interface TipoPersonasRepository extends JpaRepository<TipoPersonas, Integer> {

	
	Optional<TipoPersonas> findById(Integer codTipoPersonas);

	
	Page<TipoPersonas> findAllByOrderByDescripcionAsc(Pageable pageable);

	
	Page<TipoPersonas> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	
	void deleteById(Integer codTipoPersonas);

}