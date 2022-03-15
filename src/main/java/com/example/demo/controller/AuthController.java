package com.example.demo.controller;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LoginCredentials;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JWTUtil;
import com.example.demo.service.UserService;


@RestController
public class AuthController {

	/**
	 * INYECTAMOS EL REPOSITORIO DEL USUARIO, LA CLASE JWTUTIL PERTENECIENTE AL PAQUETE DE SEGURIDAD
	 * Y DOS CLASES PROPIAS DE JAVA
	 */
    @Autowired private UserRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    /**
     * INYECYAMOS EL SERVICIO DEL USUARIO
     */
    @Autowired private UserService userService;

    /**
     * POSTMAPPING QUE NOS PERMITE REGISTRAR USUARIOS EN NUESTRA APLICACIÓN
     * @param user
     * @param body
     * @return
     * SI EL EMAIL QUE ES USADO PARA EL REGISTRO YA ESTA EN LA BASE DE DATOS NOS DARA UNA EXCEPCIÓN
     * @throws Exception 
     */
    @PostMapping("/auth/register")
    public Map<String, Object> registerHandler(@RequestBody User user, User body) throws Exception{
    	
    	if (userRepo.findByEmail(user.getEmail())==null) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("access_token", token);
    	}
	    else {
			throw new Exception();
		}
    }

    /**
     * POSTMAPPING QUE RECIBE LOS DATOS INTRODUCIDOS POR EL CLIENTE.
     * @param body
     * @return
     * SI LOS DATOS INTRODUCIDOS POR EL CLIENTE EXISTEN GENERARA UN TOKEN Y LO MANDARA AL FRONT END.
     * 
     * SI LOS DATOS INTRODUCIDOS POR EL CLIENTE NO EXISTEN NOS DARA DOS TIPOS DE EXCEPCIONES, COMPRUEBA SI 
     * EL DATO INCORRECTO ES EL EMAIL, LA CONTRASEÑA U AMBOS.
     * @throws Exception 
     */
    @PostMapping("/auth/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) throws Exception{
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("access_token", token);
        }catch (AuthenticationException authExc){
        	if(this.userRepo.getEmail(body.getEmail()) != null) {
        		throw new Exception();
        	}
        	else {
        		throw new Exception();
        	}
            	
        }
    }

    
}