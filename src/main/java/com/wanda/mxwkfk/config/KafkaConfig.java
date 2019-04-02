package com.wanda.mxwkfk.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
	@Value("${bootstrap.servers}")
	public String bootStrapServers;
	@Value("${group.id}")
	public String groupId;
	@Value("${enable.auto.commit}")
	public String enableAutoCommit;
	@Value("${session.timeout.ms}")
	public String sessionTimeOutMs;
	@Value("${max.poll.records}")
	public int maxPollRecords;
	@Value("${key.deserializer}")
	public String keyDeserializer;
	@Value("${value.deserializer}")
	public String valueDeserializer;
	
	@Value("${kakfa.topics}")
	public String kafkaTopics;
	@Value("${kakfa.brokers}")
	public String kafkaBrokers;
	@Value("${zookeeperlist.server}")
	public String zkServer;
	@Value("${zookeeperlist.port}")
	public String zkPort;
	
	@Deprecated
	@Bean(name="kafkaProps1")
	public Properties prop1() {
		Properties properties1 = new Properties();
		properties1.put("bootstrap.servers", "192.168.0.14:9092,192.168.0.15:9092");// 172.16.2.112,172.16.2.113
		properties1.put("group.id", "test");
		properties1.put("enable.auto.commit", "false");
		properties1.put("session.timeout.ms", "30000");
		properties1.put("max.poll.records", 100);
		properties1.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties1.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return properties1;
	}
	
	@Bean(name="kafkaProps2")
	public Properties kafkaProperties() {
		Properties properties1 = new Properties();
		properties1.put("bootstrap.servers", bootStrapServers);// 172.16.2.112,172.16.2.113
		properties1.put("group.id", groupId);
		properties1.put("enable.auto.commit", enableAutoCommit);
		properties1.put("session.timeout.ms", sessionTimeOutMs);
		properties1.put("max.poll.records", maxPollRecords);
		properties1.put("key.deserializer", keyDeserializer);
		properties1.put("value.deserializer", valueDeserializer);
		return properties1;
	}
}
