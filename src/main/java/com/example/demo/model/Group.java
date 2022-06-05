package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "grupo")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@OneToMany
	private List<GroupMember> groupMembers = new ArrayList<>();


	/**
	 * Constructor vacio.
	 */
	public Group() {
	}

	/**
	 * Constructor con el nombre del grupo.
	 * 
	 * @param name
	 */
	public Group(String name) {
		this.name = name;
	}
	
	public Group(String name, List<GroupMember> groupMembers) {
		this.name = name;
		this.groupMembers = groupMembers;
	}

	/**
	 * Getters y setters del grupo.
	 * 
	 * @return
	 */

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	/**
	 * Hascode y qeuals de la id del grupo
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return Objects.equals(id, other.id);
	}
	
	
	/**
	 * ToString con todos los atributos del grupo
	 */
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
	
	
	public void deleteMember (GroupMember member) {
		this.groupMembers.remove(member);
	}

}
