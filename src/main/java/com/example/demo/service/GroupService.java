package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberNotAddedException;
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
	 * Consigue un grupo concreto Comprueba que el grupo exista Comprueba que el
	 * usuario que realiza la consulta sea miembro de ese grupo
	 * 
	 * @param id.
	 * @return grupo por id
	 */
	public Group getGroup(Long id, User user) {
		checkGroup(id);
		Group g = groupRepo.findById(id).get();
		Boolean found = false;
		for (GroupMember member : g.getGroupMembers()) {
			if (member.getUser().equals(user)) {
				found = true;
			}
		}
		if (!found) {
			throw new MemberNotAddedException();
		}
		return g;
	}

	/**
	 * Comprueba que un grupo exista
	 * 
	 * @param idGroup
	 * @return grupo
	 */
	private void checkGroup(Long idGroup) {
		if (!groupRepo.existsById(idGroup)) {
			throw new GroupNotFoundException();
		}
	}

	/**
	 * Consigue todos los grupos de un usuario
	 * 
	 * @param user
	 * @return lista de grupos de un usuario
	 */
	public List<Group> getGroupsFromUser(User user) {
		return groupRepo.getGroupsFromUser(user.getId());
	}

	/**
	 * Crea un nuevo grupo
	 * 
	 * @param group
	 * @throws RepeatedMembersFoundException si los miembros del equipo que se
	 *                                       quiere formar están repetidos
	 * @return grupo nuevo
	 */
	public Group addGroup(Group group) {
		// Comprobamos que no haya repeticiones
		Set<GroupMember> members = new HashSet<>(group.getGroupMembers());
		if (members.size() < group.getGroupMembers().size()) {
			throw new RepeatedMembersFoundException();
		}

		// guardamos miembros del grupo y al user
		for (GroupMember member : group.getGroupMembers()) {
			groupMemberRepo.save(member);
		}
		// gruardamos grupo
		return groupRepo.save(group);
	}

	/**
	 * Elimina un grupo
	 * 
	 * @param id
	 */
	public void deleteGroup(Long id) {
		Group g = groupRepo.getById(id);
		checkGroup(id);
		List<GroupMember> members = g.getGroupMembers();
		List<GroupMember> members2 = new ArrayList<>(members);
		g.getGroupMembers().removeAll(members);
		groupRepo.save(g);
		groupMemberRepo.deleteAll(members2);
		groupRepo.deleteById(id);
	}

}
