package com.example.msemprunt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsempruntApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsempruntApplication.class, args);
	}

}
