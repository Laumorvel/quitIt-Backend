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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberAlreadyExistingException;
import com.example.demo.error.MemberNotAddedException;
import com.example.demo.error.MemberNotAdminException;
import com.example.demo.error.ModifyingSelfGroupMemberException;
import com.example.demo.error.RepeatedMembersFoundException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.GroupService;

@RestController
public class GroupController {

	@Autowired
	GroupRepository groupRepo;

	@Autowired
	GroupService groupService;

	@Autowired
	UserRepo userRepo;

	/**
	 * Método para comprobar que el usuario que contiene el token es correcto.
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
	 * Consigue el grupo con una id concreta
	 * 
	 * @param id
	 * @return grupo concreto
	 */
	@GetMapping("/group/{id}")
	public Group getGroup(@PathVariable Long id) {
		checkUser();
		return groupService.getGroup(id);
	}
	
	/**
	 * Consigue todos los grupos de un usuario
	 * @return lista de grupos
	 */
	@GetMapping("/groups")
	public List<Group> getgroupsFromUser(){
		return groupService.getGroupsFromUser(checkUser());
	}

	/**
	 * Crea un nuevo grupo
	 * 
	 * @param group
	 * @return grupo nuevo
	 */
	@PostMapping("/group")
	public Group addGroup(@RequestBody Group group) {
		checkUser();
		return groupService.addGroup(group);
	}

	/**
	 * Elimina un grupo
	 * 
	 * @param id
	 */
	@DeleteMapping("/group/{id}/delete")
	public void deleteGroup(@RequestParam Long id) {
		checkUser();
		groupService.deleteGroup(id);
	}

	/**
	 * Añade un nuevo miembro a un grupo ya creado
	 * 
	 * @param member
	 * @param id
	 * @return grupo con nuevo miembro
	 */
	@PostMapping("/group/{id}/member")
	public Group addNewMember(@RequestBody GroupMember member, @RequestParam Long id) {
		checkUser();
		return groupService.addNewMember(member, id);
	}

	// EXCEPCIONES--------------------------------------------------------

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
	 * Indica que el miembro del grupo que se quiere modificar no forma parte del
	 * equipo
	 * 
	 * @param ex
	 * @return conflict
	 * @throws Exception
	 */
	@ExceptionHandler(MemberNotAddedException.class)
	public ResponseEntity<ApiError> MemberNotAddedException(MemberNotAddedException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

	/**
	 * Uno mismo no puede modificar su propio miembro de grupo. Será otro
	 * administrador el que decida si puede o no ser admin o miembro normal.
	 * 
	 * @param ex
	 * @return conflicto
	 * @throws Exception
	 */
	@ExceptionHandler(ModifyingSelfGroupMemberException.class)
	public ResponseEntity<ApiError> ModifyingSelfGroupMemberException(ModifyingSelfGroupMemberException ex)
			throws Exception {
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
	 * El usuario que realiza las modificaciones en la bbd sobre otro miembro del
	 * grupo debe ser admin
	 * 
	 * @param ex
	 * @return conflict
	 * @throws Exception
	 */
	@ExceptionHandler(MemberNotAdminException.class)
	public ResponseEntity<ApiError> MemberNotAdminException(MemberNotAdminException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	/**
	 * Ocurre cuando varios miemnbros se repiten al intentar crear un grupo
	 * @param ex
	 * @return excepción con traza controlada - conflict
	 * @throws Exception
	 */
	@ExceptionHandler(RepeatedMembersFoundException.class)
	public ResponseEntity<ApiError> RepeatedMembersFoundException(RepeatedMembersFoundException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	/**
	 * Excepción que muestra que el usuario no existe
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> userNotFound(UserNotFoundException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

}
