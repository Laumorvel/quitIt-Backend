package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GroupMember;
import com.example.demo.repository.GroupMemberRepository;

@Service
public class GroupMemberService {
	
	@Autowired
	GroupMemberRepository groupMemberRepo;
	
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
	public List<GroupMember> getMembersOfAGroup(Long idGroup){
		return groupMemberRepo.findMembersOfAGroup(idGroup);
	}

}
