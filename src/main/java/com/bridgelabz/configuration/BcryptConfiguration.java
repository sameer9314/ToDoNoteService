package com.bridgelabz.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Purpose : Configuration class to encode password.
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    21/07/2018
 */
@Configuration
public class BcryptConfiguration {

	/**
	 * To encrypt the password.
	 * 
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
