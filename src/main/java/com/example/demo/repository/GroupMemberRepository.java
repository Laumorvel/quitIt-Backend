package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	/**
	 * Selecciona todos los miembros de un equipo
	 * @param idGroup
	 * @return miembros de un equipo
	 */
	@Query(value = "SELECT gm FROM group_member gm, grupo_group_members ggm WHERE ggm.grupo_id = ?1 AND ggm.group_members_id = gm.id", nativeQuery = true)
	List<GroupMember> findMembersOfAGroup(Long idGroup);

	/**
	 * Query que devuelve una lista de todos los meet ups de la base de datos
	 * @return
	 */
	@Query(value="SELECT group_members_id FROM grupo_group_members", nativeQuery = true)
	List<GroupMember> findAllGroupMembers();
	
}
