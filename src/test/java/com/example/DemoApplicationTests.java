package com.example;

import com.example.application.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
