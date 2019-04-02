package com.wanda.mxwkfk.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanda.mxwkfk.config.KafkaProperties;
import com.wanda.mxwkfk.service.CrudTableService;

public class TopicPartitionThread extends Thread {

	private static Logger logger = LoggerFactory.getLogger(TopicPartitionThread.class);
	private ExecutorService workerExecutorService;

	private Semaphore semaphore;

	private Map<TopicPartition, OffsetAndMetadata> offsetsMap = new HashMap<>();

	private List<Future<String>> taskList = new ArrayList<>();

	public TopicPartitionThread(ExecutorService workerExecutorService, Semaphore semaphore) {
		this.workerExecutorService = workerExecutorService;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		Properties properties2 = KafkaProperties.properties2;
		String brokers = properties2.getProperty("kafka.brokers");
		String topics = properties2.getProperty("kafka.topics");
		String zookeeperserver = properties2.getProperty("zookeeperlist.server");
		String zookeeperport = properties2.getProperty("zookeeperlist.port");
		logger.info("kafka.brokers:" + brokers);
		logger.info("kafka.brokers:" + brokers);
		logger.info("kafka.topics:" + topics);
		logger.info("zookeeperlist.server:" + zookeeperserver);
		logger.info("zookeeperlist.port:" + zookeeperport);
		if (StringUtils.isEmpty(brokers) || StringUtils.isEmpty(topics)) {
			System.out.println("Kafka参数异常！");
			System.exit(0);
		}
		final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaProperties.properties1);
		consumer.subscribe(Arrays.asList(topics), new ConsumerRebalanceListener() {

			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				logger.info("threadId = {}, onPartitionsRevoked.", Thread.currentThread().getId());
				consumer.commitSync(offsetsMap);
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				logger.info("threadId = {}, onPartitionsAssigned.", Thread.currentThread().getId());
				offsetsMap.clear();
				taskList.clear();
			}
		});

		CrudTableService crudTableService = new CrudTableService(zookeeperserver, zookeeperport);
		while (Cache.getInstance().isKafkaThreadStatus()) {
			try {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (final ConsumerRecord<String, String> record : records) {
					semaphore.acquire();
					System.out.println(record.value());
					crudTableService.parseRawJson(record.value());
					TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
					OffsetAndMetadata offset = new OffsetAndMetadata(record.offset());
					offsetsMap.put(topicPartition, offset);
					taskList.add(workerExecutorService
							.submit(new WorkThread(record.topic(), record.value(), semaphore)));
				}

				for (Future<String> task : taskList) {
					task.get();
				}
				consumer.commitSync();
			} catch (Exception e) {
				logger.error("TopicPartitionThread run error.", e);
			} finally {
				taskList.clear();
			}
		}
		consumer.close();
	}

}
