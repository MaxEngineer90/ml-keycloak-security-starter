package io.github.max.engineer.ml.keycloak.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for ML Keycloak Security Starter.
 * This Spring Boot application provides security integration with Keycloak.
 */
@SpringBootApplication
public class MlKeycloakSecurityStarterApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(MlKeycloakSecurityStarterApplication.class, args);
	}

}
