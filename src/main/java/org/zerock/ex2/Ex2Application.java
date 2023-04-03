package org.zerock.ex2;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class Ex2Application {

	public static void main(String[] args) {
		log.info("됨?");
		SpringApplication.run(Ex2Application.class, args);
	}

}