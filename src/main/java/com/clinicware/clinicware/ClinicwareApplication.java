package com.clinicware.clinicware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "ClinicWare Patients Management API",
		version = "1.0",
		description = "Backend for the Angular Patients management app",
		contact = @Contact(
			name = "José Júnior", 
			email = "josejunior.jnj08@gmail.com",
			url = "https://github.com/kipFaceless"
		)
	)
)
public class ClinicwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicwareApplication.class, args);
	}

}
