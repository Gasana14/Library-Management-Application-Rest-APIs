package com.Library.Management.Rest.APIs;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
