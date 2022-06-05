package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Group;
import com.example.demo.model.MeetUp;

public interface GroupRepository extends JpaRepository<Group, Long>{
	
	/**
	 * Consigue todos los grupos de un usuario
	 * @param userId
	 * @return lista de grupos
	 */
	@Query(value="SELECT * FROM grupo WHERE id IN ("
			+ "SELECT ggm.grupo_id FROM grupo_group_members ggm, group_member gm WHERE gm.user_id = ?1 "
			+ "AND ggm.group_members_id = gm.id) ", nativeQuery = true)
	List<Group>getGroupsFromUser(Long userId);
	
	

}
