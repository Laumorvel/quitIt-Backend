package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.PenaltyAlreadyAddedException;
import com.example.demo.error.PenaltyNotFoundException;
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
	
	/**
	 * Devuelve una penalización
	 * @param id
	 * @return
	 */
	@GetMapping("/penalty/{id}")
	public Penalty getPenalty(@PathVariable Long id) {
		return penaltyService.getPenalty(id);
	}
	
	@PostMapping("/penalty")
	public Penalty addPenalty(@RequestBody Penalty penalty) {
		return penaltyService.addPenalty(penalty);
	}
	
	@DeleteMapping("penalty/{id}")
	public void deletePenalty(@PathVariable Long id) {
		penaltyService.deletePenalty(id);
	}
	
	@ExceptionHandler(PenaltyAlreadyAddedException.class)
	public ResponseEntity<ApiError> PenaltyAlreadyAddedException(PenaltyAlreadyAddedException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	@ExceptionHandler(PenaltyNotFoundException.class)
	public ResponseEntity<ApiError> PenaltyNotFoundException(PenaltyNotFoundException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

}
