package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.GroupMember;

public interface GroupMemberRepository  extends JpaRepository<GroupMember, Long>{
	
	/**
	 * Consigue todos los miembros de un grupo
	 * @param idGroup
	 * @return miembros de un grupo
	 */
	@Query(value = "SELECT * FROM group_member WHERE group_id = ?1", nativeQuery = true)
	List<GroupMember> findMembersOfAGroup(Long idGroup);
	
	/**
	 * Consigue al cargo del grupo por el id del usuario 
	 * @param idGroup
	 * @param idUser
	 * @return cargo del miembro de grupo
	 */
	@Query(value = "SELECT cargo FROM group_member WHERE user_id = ?1", nativeQuery = true)
	String findCargo(Long idUser);

}
