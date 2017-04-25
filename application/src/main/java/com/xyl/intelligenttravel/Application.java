package com.xyl.intelligenttravel;

import com.xyl.intelligenttravel.buiness.impl.HotelDispather;
import com.xyl.intelligenttravel.buiness.impl.ScenicDispatcher;
import com.xyl.intelligenttravel.buiness.impl.WeatherDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	ScenicDispatcher gyGovTask;
	@Autowired
	WeatherDispatcher weatherDispatcher;
	@Autowired
	HotelDispather hotelDispather;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication();
		springApplication.setWebEnvironment(false);
		springApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("启动");
//		weatherDispatcher.dispatch("北京");
		hotelDispather.dispatch("北京");
	}
}
