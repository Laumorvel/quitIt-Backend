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
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
<<<<<<< HEAD
=======
@Table(name = "user")
>>>>>>> laura
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

	/**
	 * Representa los días SEGUIDOS que el usuario lleva sin fumar desde que comenzó
	 * a utilizar la aplicación.
	 */
	@Column(nullable = false)
	private Integer days = 0;

	/**
	 * Representa los cigarrillos que solía fumar al día.
	 */
	@Column(nullable = false)
	private Integer cigarretes;

	/**
	 * Representa el dinero que el usuario solía gastar en tabaco a la semana.
	 * Posteriormente se realizará el cálculo de cuánto gasta al día puesto que no
	 * se suele saber cuánto se gasta al día en tabaco. Los fumadores suelen tener
	 * una idea más aproximada de lo que gastan a la semana.
	 */
	@Column(nullable = false)
	private Double money;

	/**
	 * Representa los días TOTALES que el usuario no ha fumado desde que comenzó a
	 * utilizar la aplicación.
	 */
	@Column(nullable = false)
	private Integer timeWithoutSmoking = 0;

	@JsonIgnore
	@OneToMany
	private List<Group> groupList = new ArrayList<>();

	@JsonIgnore
	@OneToMany
	private List<User> userList = new ArrayList<>();

	@JsonIgnore
	@OneToMany
	private List<Achievement> achievementList = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany
	private List<Penalty> penalties = new ArrayList<>();

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private Integer cigarettesBefore;

	@Column(nullable = false)
	private Double packagePrice;

	/**
	 * Representa los días que el usuario SÍ ha fumado desde que comenzó a utilizar
	 * la aplicación.
	 */
	@Column(nullable = false)
	private Integer smokingDays = 0;

	/**
	 * Constructor vacío.
	 */
	public User() {
	}

	/**
	 * Constructor con todos los atributos menos las listas
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param password
	 * @param days
	 * @param cigarretes
	 * @param money
	 * @param timeWithoutSmoking
	 * @param startDate
	 * @param cigarettesBefore
	 * @param packagePrice
	 * @param smokingDays
	 */
	public User(String name, String lastName, String email, String password, Integer days, Integer cigarretes,
			Double money, Integer timeWithoutSmoking, LocalDate startDate, Integer cigarettesBefore,
			Double packagePrice, Integer smokingDays) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.days = days;
		this.cigarretes = cigarretes;
		this.money = calculaDineroDiario(money);
		this.timeWithoutSmoking = timeWithoutSmoking;
		this.startDate = startDate;
		this.cigarettesBefore = cigarettesBefore;
		this.packagePrice = packagePrice;
		this.smokingDays = smokingDays;
	}

	/**
	 * Constructor con todos los atributos de user
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param password
	 * @param days
	 * @param cigarretes
	 * @param money
	 * @param timeWithoutSmoking
	 * @param groupList
	 * @param userList
	 * @param achievementList
	 * @param startDate
	 * @param cigarettesBefore
	 * @param packagePrice
	 * @param smokingDays
	 */
	public User(String name, String lastName, String email, String password, Integer days, Integer cigarretes,
			Double money, Integer timeWithoutSmoking, List<Group> groupList, List<User> userList,
			List<Achievement> achievementList, LocalDate startDate, Integer cigarettesBefore, Double packagePrice,
			Integer smokingDays) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.days = days;
		this.cigarretes = cigarretes;
		this.money = calculaDineroDiario(money);
		this.timeWithoutSmoking = timeWithoutSmoking;
		this.groupList = groupList;
		this.userList = userList;
		this.achievementList = achievementList;
		this.startDate = startDate;
		this.cigarettesBefore = cigarettesBefore;
		this.packagePrice = packagePrice;
		this.smokingDays = smokingDays;
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

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getCigarretes() {
		return cigarretes;
	}

	public void setCigarretes(Integer cigarretes) {
		this.cigarretes = cigarretes;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getTimeWithoutSmoking() {
		return timeWithoutSmoking;
	}

	public void setTimeWithoutSmoking(Integer timeWithoutSmoking) {
		this.timeWithoutSmoking = timeWithoutSmoking;
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

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

	public void setAchievementList(List<Achievement> achievementList) {
		this.achievementList = achievementList;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Integer getCigarettesBefore() {
		return cigarettesBefore;
	}

	public void setCigarettesBefore(Integer cigarettesBefore) {
		this.cigarettesBefore = cigarettesBefore;
	}

	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}

	public Integer getSmokingDays() {
		return smokingDays;
	}

	public void setSmokingDays(Integer smokingDays) {
		this.smokingDays = smokingDays;
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

	/**
	 * ToString con todos los datos del usuario
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", days=" + days + ", cigarretes=" + cigarretes + ", money=" + money
				+ ", timeWithoutSmoking=" + timeWithoutSmoking + ", groupList=" + groupList + ", userList=" + userList
				+ ", achievementList=" + achievementList + ", startDate=" + startDate + ", cigarettesBefore="
				+ cigarettesBefore + ", packagePrice=" + packagePrice + ", smokingDays=" + smokingDays + "]";
	}

	// --- MÉTODOS PROPIOS ---//

	/**
	 * Método que calcula y redondea a dos decimales el dinero gastado diariamente
	 * por el usuario.
	 * 
	 * @param dineroSemanal
	 * @return
	 */
	public Double calculaDineroDiario(Double dineroSemanal) {
		return Math.round((dineroSemanal / 7) * 100d) / 100d;
	}

}
