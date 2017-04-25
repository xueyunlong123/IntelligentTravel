package com.xyl.intelligenttravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
		basePackages = "com.xyl"
)
public class CrawlerTravelWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerTravelWebApplication.class, args);
	}
}
