package com.wanda.mxwkfk;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanda.mxwkfk.config.KafkaConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaxwellKafkaParserApplication.class)
public class HelloWorldTest {
	@Autowired
	KafkaConfig confg;

	@Autowired
	@Qualifier("prop2")
	Properties properties2;
	
	@Test
	public void testHello() {
		System.out.println("Hello world");
	}

	@Test
	public void testProp() {
		System.out.println("Begin!");
		System.out.println(properties2);
	}
}
