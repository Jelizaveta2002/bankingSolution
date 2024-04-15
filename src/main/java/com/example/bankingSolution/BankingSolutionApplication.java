package com.example.bankingSolution;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.bankingSolution.dao")
@SpringBootApplication
public class BankingSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSolutionApplication.class, args);
	}

}
