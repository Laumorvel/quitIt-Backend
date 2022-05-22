package com.example.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.GroupNotFoundException;
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
	 * @param gm
	 * @return groupMember nuevo
	 */
	public GroupMember addGroupMember(GroupMember gm) {
		return groupMemberRepo.save(gm);
	}
	
	/**
	 * Busca la lista de miembros de un grupo
	 * @param idGroup
	 * @return miembros de un grupo
	 */
//	public List<GroupMember> getMembersOfAGroup(Long idGroup){
//		return groupMemberRepo.findMembersOfAGroup(idGroup);
//	}
	
	/**
	 * Modifica el cargo de un miembro.
	 * Comprueba que exista el grupo, miembro y que este forme parte del grupo indicado.
	 * Comprueba que el cambio realmente realice algún cambio.
	 * @param id
	 * @param idMember
	 * @param member
	 * @param user
	 * @return
	 */
	public GroupMember modifyMemberCategory(Long id, Long idMember, GroupMember member, User user) {
		GroupMember gm = null;
		//Comprueba que el grupo exista
		Group g;
		try {
			g = groupRepo.getById(id);
		}catch(GroupNotFoundException e) {
			throw new GroupNotFoundException();
		}
		//Comprueba que el usuario sea admin para hacer modificaciones
	
		//Comprueba que el miembro exista
		if(groupMemberRepo.findById(idMember).get() == null) {
			throw new MemberNotAddedException();
		}
		//Comprueba que el miembro esté añadido
		for (GroupMember m : g.getGroupMembers()) {
			if(Objects.equals(m.getId(), member.getId())) {
				//Comprueba que el cambio sea realmente efectivo
				if(member.getCargo().equals(m.getCargo())) {
					throw new NoChangeOfRoleException();
				}else {
					//es hace el cambio
					m.setCargo(member.getCargo());
					gm = m;
				}
			}else {
				throw new MemberNotAddedException();
			}
		}
		return groupMemberRepo.save(gm);
	}

}
