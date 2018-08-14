package com.bridgelabz.noteservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Purpose : UserServiceProxy Interface to communicate with the User Service.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 13/08/2018
 */
@FeignClient(name = "user-service", url = "localhost:9700")
public interface UserServiceProxy {

	/**
	 * Provided API URL of the User Controller from which we want to consume the property.
	 * And the method is to store the value which will be returned from 
	 * the method of provided API of the User Controller.
	 * 
	 * @return String
	 */
	@GetMapping("/user/send_message") 
	public String getMessage();
}
