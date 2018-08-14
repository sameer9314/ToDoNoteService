package com.bridgelabz.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger.web.ApiKeyVehicle;
//import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

/**
 * Purpose : To provide implementation for the swagger configuration.
 * 
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    14/15/2018
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").
				apiInfo(apiInfo()).select()
				.paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return (regex("/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ToDo Api").description("ToDo Application API reference for developers")
				.termsOfServiceUrl("http://javainuse.com").contact("sameersaurabh9314@gmail.com").license("Sameer License")
				.licenseUrl("sameersaurabh9314@gmail.com").version("1.0").build();
	}
	
	@Bean
	public SecurityConfiguration security() {
	return new SecurityConfiguration(null,null,null,null,"JWTtoken",ApiKeyVehicle.HEADER,"JWTtoken",",");
	}
}
