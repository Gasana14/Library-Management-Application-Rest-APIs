package com.Library.Management.Rest.APIs;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot Library Management APIs",
				description = "Spring boot Library Management APIs Documentation",
				version = "V1.0",
				contact = @Contact(
						name = "Gasana Theopile",
						email = "gasana141414@gmail.com",
						url = "https://github.com/Gasana14"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/Gasana14"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Library Management APIs External Documentation",
				url = "https://github.com/Gasana14"
		)
)
public class LibraryManagementRestApIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementRestApIsApplication.class, args);
		System.out.println("App Running");
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
