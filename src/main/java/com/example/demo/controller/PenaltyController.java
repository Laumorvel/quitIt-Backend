package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Penalty;
import com.example.demo.service.PenaltyService;

@RestController
public class PenaltyController {
	
	@Autowired
	private PenaltyService penaltyService;
	

	/**
	 * Da la lista de penalizaciones
	 * 
	 * @return penalizaciones
	 */
	@GetMapping("/penalty")
	public List<Penalty> getAllPenalty() {
		return penaltyService.getAllPenalty();
	}

}
