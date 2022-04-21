package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.MeetUp;

public interface MeetUpRepo extends JpaRepository<MeetUp, Long> {

	 LocalDateTime fecha = LocalDateTime.now();
	
	
	/**
	 * Query que devuelve una lista de todos los meet ups de la base de datos
	 * @return
	 */
	@Query(value="select * from meet_up ", nativeQuery = true)
	List<MeetUp> findAllMeetUps();


	
}
