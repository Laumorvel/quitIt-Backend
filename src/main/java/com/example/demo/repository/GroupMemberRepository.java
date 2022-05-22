package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.GroupMember;

public interface GroupMemberRepository  extends JpaRepository<GroupMember, Long>{
	


}
