package com.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class RegistrationBackendApplication {

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		ApplicationContext applicationContext =
				SpringApplication.run(RegistrationBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostConstruct
	public void setUp() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		objectMapper.registerModule(new JavaTimeModule());
	}
}
