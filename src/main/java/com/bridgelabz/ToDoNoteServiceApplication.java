package com.bridgelabz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Purpose : Main class for ToDO NoteService Application.	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    11/08/2018
 */
@EnableEurekaClient
@SpringBootApplication
@RibbonClient(name="note-service")
@EnableFeignClients("com.bridgelabz")
public class ToDoNoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoNoteServiceApplication.class, args);
	}
}
