package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;

@RestController
public class GroupController {
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	GroupService groupService;
	
	/**
	 * Consigue el grupo su id
	 * @param id
	 * @return grupo concreto
	 */
	@GetMapping("/group/{id}")
	public Group getGroup(@PathVariable Long id) {
		return groupService.getGroup(id);
	}
	
	/**
	 * Crea un nuevo grupo
	 * @param group
	 * @return grupo nuevo
	 */
	@PostMapping("/group")
	public Group addGroup(@RequestBody Group group) {
		return groupService.addGroup(group);
	}

}
