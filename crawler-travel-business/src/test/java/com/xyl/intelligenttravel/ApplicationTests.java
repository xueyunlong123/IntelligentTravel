package com.xyl.intelligenttravel;

import com.xyl.intelligenttravel.buiness.impl.WeatherDispatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	WeatherDispatcher weatherDispatcher;
	@Test
	public void contextLoads() {
		weatherDispatcher.dispatch();
	}

}
