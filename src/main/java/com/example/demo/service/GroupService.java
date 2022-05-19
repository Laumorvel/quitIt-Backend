package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberAlreadyExistingException;
import com.example.demo.error.MemberNotAddedException;
import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;
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
	 * 
	 * @param id
	 * @return grupo por id
	 */
	public Group getGroup(Long id) {
		return groupRepo.findById(id).get();
	}

	/**
	 * Devuelve la lista de grupos de un usuario
	 * 
	 * @param idUser
	 * @return grupos de un usuario
	 */
	public List<Group> getGroupsByUser(Long idUser) {
		return userRepo.findById(idUser).get().getGroupList();
	}

	/**
	 * Crea un nuevo grupo
	 * 
	 * @param group
	 * @return grupo nuevo
	 */
	public Group addGroup(Group group) {
		return groupRepo.save(group);
		// user.addGroup -> user.save() -> return group.save()
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
	 * Añade un nuevo miembro al equipo. Comprueba que no sea ya parte del equipo
	 * 
	 * @param member
	 * @param id
	 * @return grupo con nuevo miembro
	 */
	public Group addNewMember(GroupMember member, Long id) {
		Group g;
		try {
			g = groupRepo.getById(id);
		} catch (GroupNotFoundException e) {
			throw new GroupNotFoundException();
		}
		for (GroupMember m : g.getGroupMembers()) {
			if (Objects.equals(m.getId(), member.getId())) {
				throw new MemberAlreadyExistingException();
			}
		}
		g.getGroupMembers().add(member);
		return groupRepo.save(g);
	}

	
	
	/**
	 * Comprueba si el miembro del grupo es administrador o no.
	 * @param member
	 * @return boolean indicando si es o no admin
	 */
	private Boolean checkAdmin(GroupMember member) {
		return member.getCargo().equals("ADMIN");
	}

}
