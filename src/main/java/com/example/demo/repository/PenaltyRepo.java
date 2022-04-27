package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Penalty;

public interface PenaltyRepo extends JpaRepository<Penalty, Long>{


	/**
	 * Query que devuelve una lista de todos las penalizaciones de la base de datos
	 * @return
	 */
	@Query(value="select * from penalty", nativeQuery = true) 
	List<Penalty> findAllPenalty();

}
