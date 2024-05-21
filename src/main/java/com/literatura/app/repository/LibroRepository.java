package com.literatura.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.literatura.app.entity.LibroEntity;

public interface LibroRepository extends JpaRepository<LibroEntity, Long> {

	@Query("SELECT l FROM LibroEntity l WHERE l.lenguaje >= :idioma")
	List<LibroEntity> findForLanguaje(String idioma);
	
}
