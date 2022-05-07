package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.EmailPasswordException;
import com.example.demo.error.PasswordException;
import com.example.demo.error.UserExistException;
import com.example.demo.model.LoginCredentials;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JWTUtil;

@RestController
public class AuthController {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Registro de usuarios, siempre se crearan con el rol de usuario y nos
	 * devolvera un token para que pueda acceder a la aplicación
	 * 
	 * @param user
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/auth/register")
	public Map<String, Object> registerHandler(@RequestBody User user, User body) throws Exception {

		if (userRepo.findByEmail(user.getEmail()) == null) {
			String encodedPass = passwordEncoder.encode(user.getPassword());
			user.setRol("USER");
			user.setStartDate(LocalDate.now());
			user.setPassword(encodedPass);
			user = userRepo.save(user);
			String token = jwtUtil.generateToken(user.getEmail(), user.getRol());
			return Collections.singletonMap("access_token", token);
		} else {
			throw new UserExistException();
		}
	}

	/**
	 * Inicio de sesión de los usuarios, distingue si el email o la contraseña son
	 * incorrectos. Tambien devuelve un token si la petición es correcta
	 * 
	 * @param body
	 * @return
	 */
	@PostMapping("/auth/login")
	public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
		try {
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					body.getEmail(), body.getPassword());

			authManager.authenticate(authInputToken);

			String rol = userRepo.findByEmail(body.getEmail()).getRol();
			String token = jwtUtil.generateToken(body.getEmail(), rol);

			return Collections.singletonMap("access_token", token);
		} catch (AuthenticationException authExc) {
			if (this.userRepo.findByEmail(body.getEmail()) != null) {
				throw new PasswordException();
			} else {
				throw new EmailPasswordException();
			}

		}
	}

	/**
	 * Comprueba que es usuario se encuentre o no logueado.
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/login")
	public ResponseEntity<String> comprobarLogueo() throws Exception {

		try {
			return ResponseEntity.ok("");
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	// -EXCEPCIONES----------------------------------------------------------------------------------------

	/**
	 * Excepción para contraseña incorrecta
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<ApiError> passwordError(PasswordException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.UNAUTHORIZED);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
	}

	/**
	 * Excepción para email incorrecto
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(EmailPasswordException.class)
	public ResponseEntity<ApiError> emailPasswordError(EmailPasswordException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.UNAUTHORIZED);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
	}

}