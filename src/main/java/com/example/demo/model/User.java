package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Usuario de la aplicación. Se han anotado como JsonIgnore todas las listas
 * para que no aparezca dicha información al buscar la información del usuario.
 * 
 * El usuario indicará sus datos como fumador al registrarse dentro de la
 * aplicación.
 * 
 * Algunos datos pueden resetearse en caso de que el usuario vuelva a fumar.
 * 
 * @author adela y laura
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String rol;

	/**
	 * Representa los días SEGUIDOS que el usuario lleva sin fumar desde que comenzó
	 * a utilizar la aplicación.
	 */
	@Column(nullable = false)
	private Integer daysInARowWithoutSmoking = 0;

	/**
	 * Los cigarrillos que ha evitado fumar desde que comenzó a usar la app
	 */
	private Integer cigarettesAvoided;

	/**
	 * Representa los días TOTALES que el usuario no ha fumado desde que comenzó a
	 * utilizar la aplicación.
	 */
	@Column(nullable = false)
	private Integer totalTimeWithoutSmoking = 0;

	@JsonIgnore
	@ManyToMany
	private List<Group> groupList = new ArrayList<>();

	@JsonIgnore
	@OneToMany
	private List<User> userList = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	private List<Achievement> achievementList = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	private List<Penalty> penalties = new ArrayList<>();

	/**
	 * La fecha en la que comenzó a utilizar la app
	 */
	private LocalDate startDate;

	/**
	 * Representa los cigarrillos que solía fumar al día.
	 */
	@Column(nullable = false)
	private Integer cigarettesBeforePerDay;

	/**
	 * Dinero que solía gastar como fumador
	 */
	@Column(nullable = false)
	private Double moneyPerDay;

	/**
	 * Representa los días que el usuario SÍ ha fumado desde que comenzó a utilizar
	 * la aplicación.
	 */
	@Column(nullable = false)
	private Integer smokingDays = 0;
	
	/**
	 * Representa los cigarrillos que el usuario sí ha fumado desde que comenzó a usar la app
	 */
	private Integer cigarettesSmoked;
	
	/**
	 * El dinero que lleva ahorrado
	 */
	private double moneySaved;
	
	@Column(nullable = false)
	private String username;

	/**
	 * Constructor vacío.
	 */
	public User() {}

	public User(String name, String lastName, String username, String email, String password, String rol, Integer cigarettesBeforePerDay,
			Double moneyPerDay) {
		this.name = name;
		this.lastName = lastName;
		this.username=username;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.cigarettesBeforePerDay = cigarettesBeforePerDay;
		this.moneyPerDay = moneyPerDay;
	}


	/**
	 * Getters y setter de user
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDaysInARowWithoutSmoking() {
		return daysInARowWithoutSmoking;
	}

	public void setDaysInARowWithoutSmoking(Integer daysInARowWithoutSmoking) {
		this.daysInARowWithoutSmoking = daysInARowWithoutSmoking;
	}

	public Integer getCigarettesAvoided() {
		return cigarettesAvoided;
	}

	public void setCigarettesAvoided(Integer cigarettesAvoided) {
		this.cigarettesAvoided = cigarettesAvoided;
	}

	public Integer getTotalTimeWithoutSmoking() {
		return totalTimeWithoutSmoking;
	}

	public void setTotalTimeWithoutSmoking(Integer totalTimeWithoutSmoking) {
		this.totalTimeWithoutSmoking = totalTimeWithoutSmoking;
	}

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

	public void setAchievementList(List<Achievement> achievementList) {
		this.achievementList = achievementList;
	}

	public List<Penalty> getPenalties() {
		return penalties;
	}

	public void setPenalties(List<Penalty> penalties) {
		this.penalties = penalties;
	}

	public Integer getCigarettesBeforePerDay() {
		return cigarettesBeforePerDay;
	}

	public void setCigarettesBeforePerDay(Integer cigarettesBeforePerDay) {
		this.cigarettesBeforePerDay = cigarettesBeforePerDay;
	}

	public Double getMoneyPerDay() {
		return moneyPerDay;
	}

	public void setMoneyPerDay(Double moneyPerDay) {
		this.moneyPerDay = moneyPerDay;
	}

	public Integer getCigarettesSmoked() {
		return cigarettesSmoked;
	}

	public void setCigarettesSmoked(Integer cigarettesSmoked) {
		this.cigarettesSmoked = cigarettesSmoked;
	}

	public double getMoneySaved() {
		return moneySaved;
	}

	public void setMoneySaved(double moneySaved) {
		this.moneySaved = moneySaved;
	}

	public Integer getTimeWithoutSmoking() {
		return totalTimeWithoutSmoking;
	}

	public void setTimeWithoutSmoking(Integer timeWithoutSmoking) {
		this.totalTimeWithoutSmoking = timeWithoutSmoking;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	

	public Integer getSmokingDays() {
		return smokingDays;
	}

	public void setSmokingDays(Integer smokingDays) {
		this.smokingDays = smokingDays;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public double getMoneySmoker() {
		return moneySaved;
	}

	public void setMoneySmoker(double moneySmoker) {
		this.moneySaved = moneySmoker;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * HashCode y Equals de la id del usuario
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", rol=" + rol + ", daysInARowWithoutSmoking=" + daysInARowWithoutSmoking
				+ ", cigarettesAvoided=" + cigarettesAvoided + ", totalTimeWithoutSmoking=" + totalTimeWithoutSmoking
				+ ", groupList=" + groupList + ", userList=" + userList + ", achievementList=" + achievementList
				+ ", penalties=" + penalties + ", startDate=" + startDate + ", cigarettesBeforePerDay="
				+ cigarettesBeforePerDay + ", moneyPerDay=" + moneyPerDay + ", smokingDays=" + smokingDays
				+ ", cigarettesSmoked=" + cigarettesSmoked + ", moneySaved=" + moneySaved + "]";
	}

}
