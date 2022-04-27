package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CommentCommunity;
import com.example.demo.model.Incidence;

public interface IncidenceRepo extends JpaRepository<Incidence, Long>{

	/**
	 * Query que devuelve una lista de todos las incidencias de la base de datos
	 * @return
	 */
	@Query(value="SELECT * FROM incidence", nativeQuery = true)
	List<Incidence>getAllIncidences();
}
