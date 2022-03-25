package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Penalty;
import com.example.demo.repository.PenaltyRepo;

@Service
public class PenaltyService {
	
	@Autowired PenaltyRepo penaltyRepo;

	public List<Penalty> getAllPenalty() {
		return penaltyRepo.findAllPenalty();
	}

}
