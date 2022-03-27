package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Achievement;
import com.example.demo.model.User;

public interface AchievementRepo extends JpaRepository<Achievement, Long>{


	@Query(value="select * from achievement", nativeQuery = true) 
	List<Achievement> findAllAchievement();

}
