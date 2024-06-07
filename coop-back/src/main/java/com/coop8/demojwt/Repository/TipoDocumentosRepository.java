package com.coop8.demojwt.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop8.demojwt.Models.TipoDocumentos;


@Repository
public interface TipoDocumentosRepository extends JpaRepository<TipoDocumentos, Integer> {

	

	Optional<TipoDocumentos> findById(Integer codTipoDocumentos);


	
	Page<TipoDocumentos> findAllByOrderByDescripcionAsc(Pageable pageable);


	Page<TipoDocumentos> findByDescripcionContainingIgnoreCaseOrderByDescripcionAsc(String descripcion,
			Pageable pageable);

	
	void deleteById(Integer codTipoDocumentos);

}