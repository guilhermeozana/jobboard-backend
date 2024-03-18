package com.jobboard.companyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.jobboard.companyms", "com.jobboard.shared"})
public class CompanymsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanymsApplication.class, args);
	}

}
