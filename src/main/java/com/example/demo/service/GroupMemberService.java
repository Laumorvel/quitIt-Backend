package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.ActionOnlyAllowedForAdminsException;
import com.example.demo.error.GroupNotFoundException;
import com.example.demo.error.MemberAlreadyExistingException;
import com.example.demo.error.MemberNotAddedException;
import com.example.demo.error.NoChangeOfRoleException;
import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;
import com.example.demo.model.User;
import com.example.demo.repository.GroupMemberRepository;
import com.example.demo.repository.GroupRepository;

@Service
public class GroupMemberService {

	@Autowired
	GroupMemberRepository groupMemberRepo;

	@Autowired
	GroupRepository groupRepo;

	/**
	 * Añade un nuevo miembro a un grupo
	 * 
	 * @param gm
	 * @return groupMember nuevo
	 */
	public GroupMember addGroupMember(GroupMember gm) {
		return groupMemberRepo.save(gm);
	}

	/**
	 * Busca la lista de miembros de un grupo
	 * 
	 * @param idGroup
	 * @return miembros de un grupo
	 */
	public List<GroupMember> getMembersOfAGroup(Long idGroup) {
		return groupMemberRepo.findMembersOfAGroup(idGroup);
	}

	/**
	 * Añade un nuevo miembro al equipo. Comprueba que no sea ya parte del equipo
	 * 
	 * @param member
	 * @param id
	 * @return nuevo miembro
	 */
	public GroupMember addNewMember(GroupMember member, Long id) {
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
		groupMemberRepo.save(member);
		g.getGroupMembers().add(member);
		groupRepo.save(g);
		return member;
	}

	/**
	 * Modifica el cargo de un miembro. Comprueba que exista el grupo, miembro y que
	 * este forme parte del grupo indicado. Comprueba que el cambio realmente
	 * realice algún cambio.
	 * 
	 * @param id
	 * @param idMember
	 * @param member
	 * @param user
	 * @return
	 */
	public GroupMember modifyMemberCategory(Long id, Long idMember, GroupMember member, User user) {
		GroupMember gm = null;
		// Comprueba que el grupo exista
		Group g;
		try {
			g = groupRepo.getById(id);
		} catch (GroupNotFoundException e) {
			throw new GroupNotFoundException();
		}
		// Comprueba que el usuario sea admin para hacer modificaciones

		// Comprueba que el miembro exista
		if (groupMemberRepo.getById(idMember) == null) {
			throw new MemberNotAddedException();
		}
		// Comprueba que el miembro esté añadido
		Boolean found = false;
		for (GroupMember m : g.getGroupMembers()) {
			if (Objects.equals(m.getId(), member.getId())) {
				// Comprueba que el cambio sea realmente efectivo
				if (member.getCargo().equals(m.getCargo())) {
					throw new NoChangeOfRoleException();
				} else {
					found = true;
					// es hace el cambio
					m.setCargo(member.getCargo());
					gm = m;
				}
			} 
		}
		if(!found) {
			throw new MemberNotAddedException();
		}
		return groupMemberRepo.save(gm);
	}

	/**
	 * Elimina a un miembro del grupo. Solo un miembro que pertenezca a este grupo y
	 * que sea admin o el usuario que posee ese miembro podrá realizar esta acción
	 * 
	 * @param idMember
	 * @param idGroup
	 * @param user
	 */
	public void deleteGroupMember(Long idMember, Long idGroup, User user) {
		// Comprueba que exista el miembro del grupo
		Group g;
		try {
			g = groupRepo.getById(idGroup);
		} catch (GroupNotFoundException e) {
			throw new GroupNotFoundException();
		}

		// Comrpueba que exista el grupo
		GroupMember gm = null;
		try {
			gm = groupMemberRepo.findById(idMember).get();
		} catch (MemberNotAddedException e) {
			throw new MemberNotAddedException();
		}

		// Comprueba que el miembro a modificar forma parte de ese grupo
		Boolean found1 = false;
		for (GroupMember mem : g.getGroupMembers()) {
			if (mem.equals(gm)) {
				found1 = true;
			}
		}
		if (!found1) {
			throw new MemberNotAddedException();
		}

		GroupMember memberChanger;
		if (user.equals(gm.getUser())) {
			g.getGroupMembers().remove(g.getGroupMembers().indexOf(gm));
			groupRepo.save(g);
			groupMemberRepo.delete(gm);
		} else {
			Boolean found = false;
			// Comprueba que el user que quiere realizar la acción forme parte del grupo
			for (GroupMember member : g.getGroupMembers()) {
				if (member.getUser().equals(user)) {
					memberChanger = member;
					found = true;
					if (!memberChanger.getCargo().equals("ADMIN")) {
						throw new ActionOnlyAllowedForAdminsException();
					} else {
						g.getGroupMembers().remove(g.getGroupMembers().indexOf(gm));
						groupRepo.save(g);
						groupMemberRepo.delete(gm);
						break;
					}
				}

			}
				if (!found) {
					throw new MemberNotAddedException();
				}
		}
	}
	
}
