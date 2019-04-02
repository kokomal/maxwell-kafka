package com.wanda.mxwkfk.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;

@Deprecated
public class KafkaProperties {

	public static final Properties properties1 = new Properties();

	private static String confPath = System.getProperty("user.dir") + File.separator + "conf/0327.properties";
	public static final Properties properties2 = new Properties();

	static {
		properties1.put("bootstrap.servers", "192.168.0.14:9092,192.168.0.15:9092");// 172.16.2.112,172.16.2.113
		properties1.put("group.id", "test");
		properties1.put("enable.auto.commit", "false");
		properties1.put("session.timeout.ms", "30000");
		properties1.put("max.poll.records", 100);
		properties1.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties1.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		File file = new File(confPath);
		if (!file.exists()) {
			InputStream in = Consumer.class.getClassLoader().getResourceAsStream("0327.properties");
			try {
				properties2.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				properties2.load(new FileInputStream(confPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
