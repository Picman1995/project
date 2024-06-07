package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.EstadoCiviles;


@Repository
public interface EstadoCivilesRepository extends JpaRepository<EstadoCiviles, Integer> {


	Optional<EstadoCiviles> findById(Integer codEstadoCiviles);


	Page<EstadoCiviles> findAllByOrderByDescripcionAsc(Pageable pageable);

	
	Page<EstadoCiviles> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	
	void deleteById(Integer codEstadoCiviles);

}