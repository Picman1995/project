package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.Nacionalidades;


@Repository
public interface NacionalidadesRepository extends JpaRepository<Nacionalidades, Integer> {


	Optional<Nacionalidades> findById(Integer id);

	
	Page<Nacionalidades> findAllByOrderByDescripcionAsc(Pageable pageable);


	Page<Nacionalidades> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);


	void deleteById(Integer id);

}