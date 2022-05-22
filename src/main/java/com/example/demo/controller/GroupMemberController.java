package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.NoChangeOfRoleException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.GroupMember;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.GroupMemberService;

@RestController
public class GroupMemberController {

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	UserRepo userRepo;

	/**
	 * Método para comprobar que el usuario que contiene el token es correcto.
	 * 
	 * @return usuario en contrado
	 * @throws exception en caso de no encontrar al usuario
	 */
	private User checkUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userRepo.findByEmail(email) == null) {
			throw new UserNotFoundException();
		}
		return userRepo.findByEmail(email);
	}

	/**
	 * Crea un nuevo miembro de grupo
	 * 
	 * @param gm
	 * @return nuevo miembro de grupo
	 */
	@PostMapping("/groupMember")
	public GroupMember newGroupMember(@RequestBody GroupMember gm) {
		checkUser();
		return groupMemberService.addGroupMember(gm);
	}

	/**
	 * Consigue todos los miembros de un grupo en concreto
	 * 
	 * @param idGroup
	 * @return lista de miembros de un equipo
	 */
//	@GetMapping("/groupMember/{idGroup}")
//	public List<GroupMember> getMembersOfAGroup(@PathVariable Long idGroup) {
//		checkUser();
//		return groupMemberService.getMembersOfAGroup(idGroup);
//	}

	/**
	 * Modifica el campo cargo del miembro del grupo
	 * @param id
	 * @param idMember
	 * @param member
	 * @return miembro modificado
	 */
	@PutMapping("/group/{id}/member/{idMember}")
	public GroupMember modifyMemberCategory(@RequestParam Long id, @RequestParam Long idMember,
			@RequestBody GroupMember member) {
		return groupMemberService.modifyMemberCategory(id, idMember, member, checkUser());

	}

	/**
	 * Indica que el cambio que se quiere realizar en el miembro de un equipo no va
	 * a modificar nada (de admin a admin), por lo que no se va a hacer un gruardado
	 * en la bbdd.
	 * 
	 * @param ex
	 * @return conflict
	 * @throws Exception
	 */
	@ExceptionHandler(NoChangeOfRoleException.class)
	public ResponseEntity<ApiError> NoChangeOfRoleException(NoChangeOfRoleException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

}
