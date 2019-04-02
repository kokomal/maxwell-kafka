package com.wanda.mxwkfk;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wanda.mxwkfk.config.KafkaConfig;
import com.wanda.mxwkfk.service.CrudTableService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaxwellKafkaParserApplication.class)
public class TestConsumer {
	protected static final Log log = LogFactory.getLog(TestConsumer.class);
	@Autowired
	KafkaConfig config;
	@Autowired
	@Qualifier("kafkaProps2")
	Properties kafkaProps2;
	
	@Test
	public void testConsumer() {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProps2);
		consumer.subscribe(Arrays.asList(config.kafkaTopics));
		CrudTableService crudTableService = new CrudTableService(config.zkServer, config.zkPort);
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("kafka111" + record.value());
				crudTableService.parseRawJson(record.value());
				System.out.println("kafka" + record.value());
			}
			consumer.close();
		}
	}
}
