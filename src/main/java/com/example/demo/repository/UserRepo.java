package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.User;



/**
 * INTERFAZ QUE NOS PERMITE COMUNICARNOS CON EL REPOSITORIO DEL USUARIO
 * @author adela
 *
 */
public interface UserRepo extends JpaRepository<User, Long> {
  
	
	/**
	 * METODO QUE NOS ENCUENTRA EL EMAIL DEL USUARIO
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);
	
	/**
	 * Query para conseguir a un usuario a través de una cadena de string recibida
	 * @param busqueda
	 * @return
	 */
	@Query(value = "SELECT * FROM user WHERE username LIKE %:username%", nativeQuery = true)
	public List<User> findByUsername(String username);

	
	/**
	 * Query que nos consigue todos los usuarios que tenemos registrados en la base de datos
	 * @return
	 */
	@Query(value="select * from user", nativeQuery = true) 
	public List<User> findAllUsers();

	
	/**
	 * Query para conseguir a un usuario a través del id de su imagen
	 * @param fileId
	 * @return user
	 */
	@Query(value="SELECT * FROM user WHERE file_id=?1", nativeQuery = true)
	public User getUserFromFileId(String fileId);

	/**
	 * Query para conseguir a un usuario a través de su username
	 * @param busqueda
	 * @return
	 */
	@Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
	public User findByUsernameComplete(String username);

	@Query(value = "SELECT user_list_id FROM user_user_list WHERE user_id = ?1", nativeQuery = true)
	public List<Long> searchFriends(Long long1);


	
}