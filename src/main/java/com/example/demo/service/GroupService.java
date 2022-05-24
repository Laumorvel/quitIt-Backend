package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberAlreadyExistingException;
import com.example.demo.error.RepeatedMembersFoundException;
import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;
import com.example.demo.model.User;
import com.example.demo.repository.GroupMemberRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepo;

@Service
public class GroupService {

	@Autowired
	GroupRepository groupRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	GroupMemberRepository groupMemberRepo;

	/**
	 * Consigue un grupo concreto
	 * 
	 * @param id.
	 * @return grupo por id
	 */
	public Group getGroup(Long id) {
		return groupRepo.findById(id).get();
	}
	
	/**
	 * Consigue todos los grupos de un usuario
	 * @param user
	 * @return lista de grupos de un usuario
	 */
	public List<Group> getGroupsFromUser(User user){
		return groupRepo.getGroupsFromUser(user.getId());
	}

	/**
	 * Devuelve la lista de grupos de un usuario
	 * 
	 * @param idUser
	 * @return grupos de un usuario
	 */
	

	/**
	 * Crea un nuevo grupo
	 * 
	 * @param group
	 * @throws RepeatedMembersFoundException si los miembros del equipo que se
	 *                                       quiere formar están repetidos
	 * @return grupo nuevo
	 */
	public Group addGroup(Group group) {
		//Comprobamos que no haya repeticiones
		Set<GroupMember> members = new HashSet<>(group.getGroupMembers());
		if (members.size() < group.getGroupMembers().size()) {
			throw new RepeatedMembersFoundException();
		}
		
		//guardamos miembros del grupo y al user
		for (GroupMember member : group.getGroupMembers()) {
			groupMemberRepo.save(member);
		}
		//gruardamos grupo
		return groupRepo.save(group);
	}

	/**
	 * Elimina un grupo
	 * 
	 * @param id
	 */
	public void deleteGroup(Long id) {
		groupRepo.deleteById(id);
	}



	/**
	 * Comprueba si el miembro del grupo es administrador o no.
	 * 
	 * @param member
	 * @return boolean indicando si es o no admin
	 */
	private Boolean checkAdmin(GroupMember member) {
		return member.getCargo().equals("ADMIN");
	}

}
