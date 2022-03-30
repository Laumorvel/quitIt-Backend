package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	static final String urlFront = "http://localhost:4200";

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
			

				/**
				 * CORS PARA PODER HACER LOGIN
				 */
				registry.addMapping("/auth/login").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "DELETE", "OPTIONS", "PUT", "Content-Type", "X-Requested-With",
								"accept", "Origin", "Access-Control-Request-Method", "Authorization",
								"Access-Control-Request-Headers")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				/**
				 * CORS PARA PODER HACER UN REGISTRO DE UN USUARIO
				 */
				registry.addMapping("/auth/register").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
								"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
						.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

				registry.addMapping("/user").allowedOrigins(urlFront)
						.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
								"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
								"Authorization")
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

				registry.addMapping("/commentsCommunity").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				registry.addMapping("/commentsCommunity/{idC}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				registry.addMapping("/incidence").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				

				registry.addMapping("/incidence/{idi}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				registry.addMapping("/users").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				registry.addMapping("/achievement").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				registry.addMapping("/penalty").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
				registry.addMapping("/meetUp").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				//Añadir una imagen
				registry.addMapping("/file").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				//Modificar la imagen de un usuario
				registry.addMapping("/file/{id}").allowedOrigins(urlFront)
				.allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				//Añadirle la imagen al usuario
				registry.addMapping("/file/user").allowedOrigins(urlFront)
				.allowedHeaders("GET", "POST", "OPTIONS", "PUT", "Content-Type", "X-Requested-With", "accept",
						"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
						"Authorization")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
				
				
			};

		};
	}

}
