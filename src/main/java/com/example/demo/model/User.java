package com.example.demo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import javax.persistence.OneToOne;

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
	private String password;// debe poder cambiarse

	private String rol;

	/**
	 * Representa los días SEGUIDOS que el usuario lleva sin fumar desde que comenzó
	 * a utilizar la aplicación.
	 */
	@Column(nullable = false)
	private Integer daysInARowWithoutSmoking = 0;// debe cambiarse

	/**
	 * Los cigarrillos que ha evitado fumar desde que comenzó a usar la app
	 */
	private Integer cigarettesAvoided;// debe cambiarse (cuenta sola menos el numero de cigarros fumados
										// exporádicamente)

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
	private LocalDate startDate = LocalDate.now();

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
	 * Representa los cigarrillos que el usuario sí ha fumado desde que comenzó a
	 * usar la app
	 */
	private Integer cigarettesSmoked = 0;

	/**
	 * El dinero que lleva ahorrado
	 */
	private double moneySaved;

	@Column(nullable = false)
	private String username;

	@OneToOne
	private File file;

	/**
	 * Indica la última fecha en la que el usuario marcó que fumó Se sustituye cada
	 * vez que el usuario marca que se ha fumado
	 */
	private LocalDate lastDateSmoking;

	/**
	 * Constructor vacío.
	 */
	public User() {
	}

	public User(Long id, String name, String lastName, String email, String password, String rol,
			Integer daysInARowWithoutSmoking, Integer cigarettesAvoided, Integer totalTimeWithoutSmoking,
			List<Group> groupList, List<User> userList, List<Achievement> achievementList, List<Penalty> penalties,
			LocalDate startDate, Integer cigarettesBeforePerDay, Double moneyPerDay, Integer smokingDays,
			Integer cigarettesSmoked, double moneySaved, String username, File file, LocalDate lastDateSmoking) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.daysInARowWithoutSmoking = daysInARowWithoutSmoking;
		this.cigarettesAvoided = cigarettesAvoided;
		this.totalTimeWithoutSmoking = totalTimeWithoutSmoking;
		this.groupList = groupList;
		this.userList = userList;
		this.achievementList = achievementList;
		this.penalties = penalties;
		this.startDate = startDate;
		this.cigarettesBeforePerDay = cigarettesBeforePerDay;
		this.moneyPerDay = moneyPerDay;
		this.smokingDays = smokingDays;
		this.cigarettesSmoked = cigarettesSmoked;
		this.moneySaved = moneySaved;
		this.username = username;
		this.file = file;
		this.lastDateSmoking = lastDateSmoking;
	}

	public User(String name, String lastName, String username, String email, String password, String rol,
			Integer cigarettesBeforePerDay, Double moneyPerDay) {
		this.name = name;
		this.lastName = lastName;
		this.username = username;
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

	/**
	 * Suma al total los cigarros indicados
	 * 
	 * @param cigarettesSmoked
	 */
	public void setCigarettesSmoked(Integer cigarettesSmoked) {
		this.cigarettesSmoked += cigarettesSmoked;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public LocalDate getLastDateSmoking() {
		return lastDateSmoking;
	}

	public void setLastDateSmoking(LocalDate lastDateSmoking) {
		this.lastDateSmoking = lastDateSmoking;
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

	// -----------------------------MÉTODOS PROPIOS--------------------------------

	/**
	 * Calcula el número de días que el usuario lleva usando la aplicación
	 * 
	 * @return integer con el número de días
	 */
	public Integer calculaDiasDesdeComienzoApp() {
		return (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
	}

	/**
	 * Calcula el número de días seguidos que el usuario lleva sin fumar Se calcula
	 * gracias al dato de la última fecha que el usuario marcó que fumó. Si es null,
	 * se calcula desde el primer día que comenzó a usar la app puesto que esto
	 * indica que desde que comenzó, no ha vuelto a fumar
	 * 
	 * @return días seguidos sin fumar (Integer)
	 */
	public void calculaTotalDaysInARowWithoutSmoking() {
		if (lastDateSmoking == null) {
			daysInARowWithoutSmoking = (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
		} else if (lastDateSmoking.isEqual(LocalDate.now())) {
			daysInARowWithoutSmoking = 0;
		} else {
			daysInARowWithoutSmoking = (int) ChronoUnit.DAYS.between(lastDateSmoking, LocalDate.now());
		}
	}

	/**
	 * Calcula el número de cigarros que el usuario ha evitado. Los cigarros que
	 * haya indicado que se ha fumado, se restarán al cálcula, que no podrá ser
	 * menor de 0.
	 * 
	 * @return cigarros evitados (0 si es 0 o menor)
	 */
	public Integer calculaCigarettesAvoided() {
		Integer cigarettes = (cigarettesBeforePerDay * calculaDiasDesdeComienzoApp()) - cigarettesSmoked;
		return (cigarettes > 0) ? cigarettes : 0;
	}

	/**
	 * TOTAL TIME WITHOUT SMOKING Calcula el número total de días en los que el
	 * usuario no ha fumado
	 * 
	 * @return dias sin fumar - Integer
	 */
	public void calcularTotalTimeWithoutSmoking() {
		totalTimeWithoutSmoking = ((int) ChronoUnit.DAYS.between(startDate, LocalDate.now())) - smokingDays;
	}

	/**
	 * SMOKING DAYS +1 Añade un día más a los días que el usuario ha fumado Setea el
	 * LAST DATE SMOKING Comprueba que no se marque dos veces el mismo día
	 */
	public void anadeDiaFumado() {
		if (lastDateSmoking == null || !lastDateSmoking.isEqual(LocalDate.now())) {
			smokingDays += 1;
		}
		lastDateSmoking = LocalDate.now();
	}

	/**
	 * Calcula el total de dinero que el usuario ha ahorrado desde el comienzo de la
	 * aplicación. Tiene en cuenta los días que el usuario ha indicado que ha fumado
	 * (se calcula en el método calcularTotalTimeWithoutSmoking())
	 * 
	 * @return número de días - Integer
	 */
	public void calculaDineroAhorrado() {
		moneySaved = Math.round((moneyPerDay * totalTimeWithoutSmoking) * 100d) / 100d;

	}

	/**
	 * Método para setear al usuario cuando marque que ha fumado. Se le pasa el
	 * número de cigarros que ha fumado ese día
	 * 
	 * @param cigarettesSmoked
	 */
	public void resetUserAfterSmoking(Integer cigarettesSmoked) {
		calculaDiasDesdeComienzoApp();// Se hace el cálculo de los días naturales desde que usa la app (sin tener
										// nada más en cuenta)
		anadeDiaFumado();// se suma 1 al número de días que ha fumado
		calcularTotalTimeWithoutSmoking();// calcula el tiempo total que lleva sin fumar
		setCigarettesSmoked(cigarettesSmoked);// Indica los cigarros que ha fumado ese día y se los suma a los que
												// llevaba
		setCigarettesAvoided(calculaCigarettesAvoided());// calcula los cigarros que ha evitado desde el comienzo (resta
															// los fumados)
		calculaTotalDaysInARowWithoutSmoking();// calcula los días seguidos que el user lleva sin fumar
		calculaDineroAhorrado();// calcula el dinero que ha ahorrado desde el comienzo, teniendo en cuenta los
								// días fumados
	}

	/**
	 * Método para setear al usuario cuando inicie sesión.
	 */
	public void setUserInitSession() {
		calculaDiasDesdeComienzoApp();
		calcularTotalTimeWithoutSmoking();
		setCigarettesAvoided(calculaCigarettesAvoided());
		calculaDineroAhorrado();
		calculaTotalDaysInARowWithoutSmoking();
	}

}
