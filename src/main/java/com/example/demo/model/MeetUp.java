package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MeetUp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private LocalDateTime date;
	private String place;
	@JsonIgnore
	@ManyToMany
	private List<User> assistantsList = new ArrayList<>();
	
	
	/**
	 * Constructor vacio
	 */
	public MeetUp() {}
	
	/**
	 * Constructor sin la lista
	 * @param title
	 * @param description
	 * @param date
	 * @param place
	 */
	public MeetUp(String title, String description, LocalDateTime date, String place) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.place = place;
	}
	
	/**
	 * Constructor con todos los atributos
	 * @param title
	 * @param description
	 * @param date
	 * @param place
	 * @param assistantsList
	 */
	public MeetUp(String title, String description, LocalDateTime date, String place, List<User> assistantsList) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
		this.place = place;
		this.assistantsList = assistantsList;
	}
	
	
	/**
	 * Getter y setter de meetUp
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public List<User> getAssistantsList() {
		return assistantsList;
	}
	public void setAssistantsList(List<User> assistantsList) {
		this.assistantsList = assistantsList;
	}
	
	/**
	 * HashCode y Equals con la id del meetUp
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
		MeetUp other = (MeetUp) obj;
		return Objects.equals(id, other.id);
	}
	
	/**
	 * ToString de todos los atributos de meetUp
	 */
	@Override
	public String toString() {
		return "MeetUp [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", place="
				+ place + ", assistantsList=" + assistantsList + "]";
	}
	
	
	
}
