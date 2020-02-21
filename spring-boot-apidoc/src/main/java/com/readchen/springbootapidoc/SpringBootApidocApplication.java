package com.readchen.springbootapidoc;

import com.terran4j.commons.api2doc.config.EnableApi2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableApi2Doc
public class SpringBootApidocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApidocApplication.class, args);
	}


}
