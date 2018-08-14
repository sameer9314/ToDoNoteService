/*package com.bridgelabz.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.Utility.ToDoInterceptor;


*//**
 * Purpose : Configuration class for Interceptor.	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    30/07/2018
 *//*
@Configuration
	public class InterceptorConfiguration implements WebMvcConfigurer {

		@Autowired
		private ToDoInterceptor toDoInterceptor;
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {

			registry.addInterceptor(toDoInterceptor).addPathPatterns("/note/*");
		}
	}
*/