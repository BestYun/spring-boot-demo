package com.readchen.springbootHikariCP;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.readchen.springbootHikariCP.mapper")
public class SpringBootHikariCpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHikariCpApplication.class, args);
	}

}
