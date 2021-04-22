package com.alex.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = "com.alex")
public class FirstSpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringBootProjectApplication.class, args);
	}
	
	
	//to join share and customer in customersharedetails
	// do this here in the same file of csd project
	/*
	@Bean
	public RestTemplate getrs()
	{
		return new RestTemplate();
	};
	
	
	copy share bean into the new project
	
	*/
	

}
