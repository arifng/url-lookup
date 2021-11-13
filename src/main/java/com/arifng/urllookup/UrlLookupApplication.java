package com.arifng.urllookup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UrlLookupApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlLookupApplication.class, args);
	}

}
