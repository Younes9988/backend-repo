package com.example.msauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableFeignClients
@SpringBootApplication
public class MsauthApplication {

	public static void main(String[] args) {
		new BCryptPasswordEncoder().encode("bibliopassword");
		SpringApplication.run(MsauthApplication.class, args);
	}

}
