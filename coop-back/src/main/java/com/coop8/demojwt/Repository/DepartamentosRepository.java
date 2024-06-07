package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Departamento;


@Repository
public interface DepartamentosRepository extends JpaRepository<Departamento, Integer> {

	/**
	 * Busca y obtiene un {Optional<Departamentos>} en base al campo descripcion
	 * 
	
	 * @param descripcion
	 * @return Optional<Departamentos>
	 */
	Optional<Departamento> findById(Integer id);

	/**
	 * Busca y obtiene un {Page<Departamentos>} de todos los departamentos
	 * 

	 * @return Page<Departamentos>
	 */
	Page<Departamento> findAllByOrderByDescripcionAsc(Pageable pageable);

	/**
	 * Busca y obtiene un {Page<Departamentos>} en base al campo descripcion
	 * 

	 * @param descripcion
	 * @return Page<Departamentos>
	 */
	Page<Departamento> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	/**
	 * Elimina un registro en base al campo id
	 * 

	 * @param id
	 */
	void deleteById(Integer id);

}