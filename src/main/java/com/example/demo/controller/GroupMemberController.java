package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GroupMember;
import com.example.demo.service.GroupMemberService;

@RestController
public class GroupMemberController {
	
	@Autowired
	GroupMemberService groupMemberService;
	
	/**
	 * Crea un nuevo miembro de grupo
	 * @param gm
	 * @return nuevo miembro de grupo
	 */
	@PostMapping("/groupMember")
	public GroupMember newGroupMember(@RequestBody GroupMember gm) {
		return groupMemberService.addGroupMember(gm);
	}
	
	/**
	 * Consigue todos los miembros de un grupo en concreto
	 * @param idGroup
	 * @return lista de miembros de un equipo
	 */
	@GetMapping("/groupMember/{idGroup}")
	public List<GroupMember> getMembersOfAGroup(@PathVariable Long idGroup){
		return groupMemberService.getMembersOfAGroup(idGroup);
	}

}
