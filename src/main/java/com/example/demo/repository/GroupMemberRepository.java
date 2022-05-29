package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	/**
	 * Selecciona todos los miembros de un equipo
	 * @param idGroup
	 * @return miembros de un equipo
	 */
	@Query(value = "SELECT gm FROM group_member gm, grupo_group_members ggm WHERE ggm.grupo_id = ?1 AND ggm.group_members_id = gm.id", nativeQuery = true)
	List<GroupMember> findMembersOfAGroup(Long idGroup);

}
