package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepo;

@Service
public class GroupService {
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	UserRepo userRepo;
	
	/**
	 * Consigue un grupo concreto
	 * @param id
	 * @return grupo por id
	 */
	public Group getGroup(Long id) {
		return groupRepo.findById(id).get();
	}
	
	/**
	 * Devuelve la lista de grupos de un usuario
	 * @param idUser
	 * @return grupos de un usuario
	 */
	public List<Group> getGroupsByUser(Long idUser){
		return userRepo.findById(idUser).get().getGroupList();
	}
	
	/**
	 * Crea un nuevo grupo
	 * @param group
	 * @return grupo nuevo
	 */
	public Group addGroup(Group group) {
		return groupRepo.save(group);
	}

}
