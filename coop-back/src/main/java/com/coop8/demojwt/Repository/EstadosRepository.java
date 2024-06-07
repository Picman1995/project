package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Estados;


@Repository
public interface EstadosRepository extends JpaRepository<Estados, Integer> {


	Optional<Estados> findById(Integer codEstados);


	Page<Estados> findAllByOrderByDescripcionAsc(Pageable pageable);

	Page<Estados> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	
	void deleteById(Integer codEstados);

}