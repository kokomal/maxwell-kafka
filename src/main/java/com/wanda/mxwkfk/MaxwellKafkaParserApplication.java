package com.wanda.mxwkfk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wanda.mxwkfk.config.KafkaConfig;

@SpringBootApplication
public class MaxwellKafkaParserApplication {
	@Autowired
	KafkaConfig config;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MaxwellKafkaParserApplication.class, args);
	}

}
