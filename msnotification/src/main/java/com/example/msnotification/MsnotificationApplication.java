package com.example.msnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class MsnotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsnotificationApplication.class, args);
	}

}
