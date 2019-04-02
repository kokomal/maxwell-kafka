package com.wanda.mxwkfk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.wanda.mxwkfk.config.KafkaProperties;
import com.wanda.mxwkfk.service.CrudTableService;

public class Consumer {
	protected static final Log log = LogFactory.getLog(Consumer.class);
	private static String confPath = System.getProperty("user.dir") + File.separator + "conf/0327.properties";

	private static Properties properties() {

		Properties properties = new Properties();
		File file = new File(confPath);
		if (!file.exists()) {
			InputStream in = Consumer.class.getClassLoader().getResourceAsStream("0327.properties");

			try {
				properties.load(in);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				properties.load(new FileInputStream(confPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	private static void consumerkafka() {
		Properties properties = properties();
		String brokers = properties.getProperty("kafka.brokers");
		String topics = properties.getProperty("kafka.topics");
		String zookeeperserver = properties.getProperty("zookeeperlist.server");
		String zookeeperport = properties.getProperty("zookeeperlist.port");
		log.info("kafka.brokers:" + brokers);
		log.info("kafka.brokers:" + brokers);
		log.info("kafka.topics:" + topics);
		log.info("zookeeperlist.server:" + zookeeperserver);
		log.info("zookeeperlist.port:" + zookeeperport);
		if (StringUtils.isEmpty(brokers) || StringUtils.isEmpty(topics)) {
			System.out.println("δ����Kafka��Ϣ...");
			System.exit(0);
		}

		KafkaConsumer<String, String> consumer = new KafkaConsumer(KafkaProperties.properties1);
		consumer.subscribe(Arrays.asList(topics));
		CrudTableService crudTableService = new CrudTableService(zookeeperserver, zookeeperport);
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("kafka111" + record.value());
				crudTableService.parseRawJson(record.value());
				System.out.println("kafka" + record.value());
			}
		}
	}

	public static void main(String[] args) {
		consumerkafka();
	}
}
