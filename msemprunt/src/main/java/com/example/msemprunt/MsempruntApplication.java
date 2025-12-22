package com.example.msemprunt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableJms
@EnableScheduling
public class MsempruntApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsempruntApplication.class, args);
	}

}
