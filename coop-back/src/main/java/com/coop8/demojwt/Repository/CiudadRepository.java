package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Ciudad;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

	/**
	 * Busca y obtiene un {OptionalC<Ciudades>} en base al campo descripcion
	 * 
	
	 * @param descripcion
	 * @return Optional<Ciudades>
	 */
	Optional<Ciudad> findById(Integer id);

	/**
	 * Busca y obtiene un {Page<Ciudades>} de todos los departamentos
	 * 

	 * @return Page<Ciudades>
	 */
	Page<Ciudad> findAllByOrderByDescripcionAsc(Pageable pageable);

	/**
	 * Busca y obtiene un {Page<Ciudades>} en base al campo descripcion
	 * 

	 * @param descripcion
	 * @return Page<Ciudades>
	 */
	Page<Ciudad> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	/**
	 * Elimina un registro en base al campo id
	 * 

	 * @param id
	 */
        void deleteById(Integer id);

}