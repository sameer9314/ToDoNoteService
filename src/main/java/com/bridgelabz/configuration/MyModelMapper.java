package com.bridgelabz.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Purpose : Class to provide configuration for the ModelMapper Class.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 21/07/2018
 */
@Configuration
public class MyModelMapper {
	/**
	 * To get the ModelMapper Object.
	 * 
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
