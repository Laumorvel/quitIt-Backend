package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	/**
	 * Variable que contiene la url del Front end
	 */
	static final String urlFront = "http://localhost:4200";

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
			

				/**
				 * Iniciar sesión los usuarios
				 */
				registry.addMapping("/auth/login").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "DELETE", "OPTIONS", "PUT", "Content-Type", "X-Requested-With",
								"accept", "Origin", "Access-Control-Request-Method", "Authorization",
								"Access-Control-Request-Headers")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				/**
				 * Registro de usuarios
				 */
				registry.addMapping("/auth/register").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
								"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				/**
				 * Modifica al usuario y el establece los datos iniciales
				 */
				registry.addMapping("/user").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Elimina un usuario por su id
				 */
				registry.addMapping("/user/{idDelete}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Enviar email
				 */
				registry.addMapping("/mail").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
								"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
								"Authorization")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

			
				registry.addMapping("/email").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
								"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
								"Authorization")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				/**
				 * Comprobación para el guardián
				 */
				registry.addMapping("/login").allowedOrigins(urlFront)
						.allowedMethods("GET", "POST", "OPTIONS", "PUT")
						.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
								"Access-Control-Request-Method", "Access-Control-Request-Headers")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				/**
				 * Mostrar todos los comentarios de la comunidad
				 */
				registry.addMapping("/commentsCommunity").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra un comentario por su id
				 */
				registry.addMapping("/commentsCommunity/{idC}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra todas las incidencias realizadas
				 */
				registry.addMapping("/incidence").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				

				/**
				 * Muestra una incidencia por su id
				 */
				registry.addMapping("/incidence/{idi}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra todos los usuarios
				 */
				registry.addMapping("/users").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra todos los logros
				 */
				registry.addMapping("/achievement").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra todos las penalizaciones
				 */
				registry.addMapping("/penalty").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Muestra todos los meets ups
				 */
				registry.addMapping("/meetUp").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Añadir una imagen
				 */
				registry.addMapping("/file").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Modificar la imagen de un usuario
				 */
				registry.addMapping("/file/{id}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Añadirle la imagen al usuario
				 */
				registry.addMapping("/file/user").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Muestra los amigos
				 */
				registry.addMapping("/friend").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "DELETE", "OPTIONS", "PUT", "Content-Type", "X-Requested-With",
						"accept", "Origin", "Access-Control-Request-Method", "Authorization",
						"Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Envía mensaje programado
				 */
				registry.addMapping("/scheduledMessage").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Crea un nuevo meet up
				 */
				registry.addMapping("/meetUp").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				
				/**
				 * Modifica el estado de la elección de la asistencia del meet up que indiquemos
				 */
				registry.addMapping("/meetUp/{id}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				/**
				 * Añade grupos
				 */
				registry.addMapping("/group").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Elimina un grupo
				 */
				registry.addMapping("/group/{id}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Añade un nuevo miembro al grupo
				 */
				registry.addMapping("/group/{id}/member").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Modifica la categoría del miembro del grupo (admin o user normal)
				 */
				registry.addMapping("/group/{id}/member/{memberId}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				/**
				 * Consigue los grupos de un usuario
				 */
				registry.addMapping("/groups").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
			};

		};
	}

}
