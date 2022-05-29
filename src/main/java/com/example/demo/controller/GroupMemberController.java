package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ActionOnlyAllowedForAdminsException;
import com.example.demo.error.ApiError;
import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberAlreadyExistingException;
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
	@GetMapping("/group/{idGroup}/groupMember/{username}")
	public List<GroupMember> getMembersOfAGroup(@PathVariable Long idGroup) {
		checkUser();
		return groupMemberService.getMembersOfAGroup(idGroup);
	}
	
	/**
	 * Añade un nuevo miembro a un grupo ya creado
	 * 
	 * @param member
	 * @param id
	 * @return grupo con nuevo miembro
	 */
	@PostMapping("/group/{id}/member")
	public GroupMember addNewMember(@RequestBody GroupMember member, @PathVariable Long id) {
		checkUser();
		return groupMemberService.addNewMember(member, id);
	}

	/**
	 * Modifica el campo cargo del miembro del grupo
	 * @param id
	 * @param idMember
	 * @param member
	 * @return miembro modificado
	 */
	@PutMapping("/group/{id}/member/{idMember}")
	public GroupMember modifyMemberCategory(@PathVariable Long id, @PathVariable Long idMember,
			@RequestBody GroupMember member) {
		return groupMemberService.modifyMemberCategory(id, idMember, member, checkUser());
	}
	
	/**
	 * Elimina a un miembro del grupo.
	 * En caso de que el miembro del grupo sea el mismo usuario, se eliminará al miembro de ese grupo (él mismo).
	 * En caso contrario, se comprobará si el usuario es admin para poder hacerlo.
	 * @param idMember
	 * @param idGroup
	 */
	@DeleteMapping("/group/{idGroup}/member/{idMember}")
	public void deleteMemberOfTeam(@PathVariable Long idMember, @PathVariable Long idGroup) {
		groupMemberService.deleteGroupMember(idMember, idGroup, checkUser());
	
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
	
	/**
	 * Indica que el grupo no se encuentra en la bbdd
	 * 
	 * @param ex
	 * @return not found
	 * @throws Exception
	 */
	@ExceptionHandler(GroupNotFoundException.class)
	public ResponseEntity<ApiError> GroupNotFoundException(GroupNotFoundException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}
	
	
	/**
	 * Indica que el miembro del grupo que se intenta añadir ya forma parte del
	 * grupo
	 * 
	 * @param ex
	 * @return conflict
	 * @throws Exception
	 */
	@ExceptionHandler(MemberAlreadyExistingException.class)
	public ResponseEntity<ApiError> MemberAlreadyExistingException(MemberAlreadyExistingException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}


	/**
	 * Excepción que resalta cuando un usuario realiza una acción que no está permitida si no es administrador
	 * @param ex
	 * @return traza controlada
	 * @throws Exception
	 */
	@ExceptionHandler(ActionOnlyAllowedForAdminsException.class)
	public ResponseEntity<ApiError> ActionOnlyAllowedForAdminsException(ActionOnlyAllowedForAdminsException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
}
