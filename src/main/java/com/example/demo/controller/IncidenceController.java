package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.IncidenceNotExist;
import com.example.demo.error.TypeMismatchException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.Incidence;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.IncidenceService;

@RestController
public class IncidenceController {

	@Autowired
	private IncidenceService incidenceService;

	@Autowired
	private UserRepo userRepo;

	/**
	 * Da la lista de incidencias
	 * 
	 * @return
	 */
	@GetMapping("/incidence")
	public List<Incidence> getAllIncidences() {
		return incidenceService.getAllIncidences();
	}

	/**
	 * Crea una incidencia
	 * 
	 * @param datos
	 * @return
	 */
	@PostMapping("/incidence")
	public Incidence createIncidence(@RequestBody Incidence datos) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return this.incidenceService.createIncidence(result, datos);
		}
	}

	/**
	 * Edita la incidencia añadiendole un comentario
	 * 
	 * @param idi
	 * @param comentario
	 * @return
	 */
	@PutMapping("/incidence/{idi}")
	public Incidence editIncidence(@PathVariable Long idi, @RequestBody(required = false) CommentCommunity comentario,
			@RequestParam(required = false) String state, @RequestParam(required = false) String envioVacio) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			Incidence incidence = incidenceService.findById(idi);
			if (state != null) {
				if (incidence == null) {
					throw new IncidenceNotExist(idi);
				} else {
					return incidenceService.changeState(state, incidence);
				}
			} else {
				if (incidence == null) {
					throw new IncidenceNotExist(idi);
				} else {

					return incidenceService.editIncidence(idi, comentario);
				}
			}

		}
	}
	
	/**
	 * Excepción que muestra que la incidencia no existe
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(IncidenceNotExist.class)
	public ResponseEntity<ApiError> IncidenceNotFound(IncidenceNotExist ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}


	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ApiError> IncidenceNotFound(TypeMismatchException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

}
