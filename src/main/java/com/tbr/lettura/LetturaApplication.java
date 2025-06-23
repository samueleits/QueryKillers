package com.tbr.lettura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LetturaApplication {

	public static void main(String[] args) {
		System.out.println("Applicazione avviata!"); // <-- QUI
		SpringApplication.run(LetturaApplication.class, args);
	}

}
