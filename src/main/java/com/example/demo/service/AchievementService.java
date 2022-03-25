package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Achievement;
import com.example.demo.repository.AchievementRepo;

@Service
public class AchievementService {

	@Autowired AchievementRepo achievementRepo;

	public List<Achievement> getAllAchievement() {
		return achievementRepo.findAllAchievement();
	}
}
