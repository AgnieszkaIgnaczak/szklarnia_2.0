package com.szklarnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//nie może być w pakiecie, bo Maven pomija pliki, aplikacja musi być w roocie
//aplikacja w SpringBoot odpala się automatycznie po wgraniu dependencies
@SpringBootApplication
public class SzklarniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SzklarniaApplication.class, args);
	}

}
