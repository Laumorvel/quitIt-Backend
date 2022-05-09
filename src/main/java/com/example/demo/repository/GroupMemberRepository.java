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

}
