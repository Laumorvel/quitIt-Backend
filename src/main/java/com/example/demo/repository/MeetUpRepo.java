package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.MeetUp;
import com.example.demo.model.User;

public interface MeetUpRepo extends JpaRepository<MeetUp, Long> {

	@Query(value="select * from meet_up", nativeQuery = true)
	List<MeetUp> findAllMeetUps();


	
}