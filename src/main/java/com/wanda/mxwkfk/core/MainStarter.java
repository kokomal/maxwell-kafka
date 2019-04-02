package com.wanda.mxwkfk.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainStarter {
	private static Logger logger = LoggerFactory.getLogger(MainStarter.class);
	private ExecutorService kafkaConsumerExecutorService;
	private ExecutorService workerExecutorService;
	private Semaphore semaphore;
	private int kafkaConsumerExecutorNumber = 5;
	private int workerExecutorNumber = 10;

	public MainStarter() {
		kafkaConsumerExecutorService = Executors.newFixedThreadPool(kafkaConsumerExecutorNumber);
		workerExecutorService = Executors.newFixedThreadPool(workerExecutorNumber);
		semaphore = new Semaphore(workerExecutorNumber);
	}

	public static void main(String[] args) throws Exception {
		MainStarter main = new MainStarter();
		main.start();
		Thread.sleep(3600 * 1000);
		main.destroy();
	}

	public void start() {
		for (int i = 0; i < kafkaConsumerExecutorNumber; i++) {
			kafkaConsumerExecutorService.submit(new TopicPartitionThread(workerExecutorService, semaphore));
		}
	}

	public void destroy() throws Exception {
		Cache.getInstance().setKafkaThreadStatus(false);
		kafkaConsumerExecutorService.shutdown();
		while (!kafkaConsumerExecutorService.awaitTermination(1, TimeUnit.SECONDS)) {
			logger.info("await kafkaConsumerExecutorService stop...");
		}
		logger.info("kafkaConsumerExecutorService stoped.");
		workerExecutorService.shutdown();
		while (!workerExecutorService.awaitTermination(1, TimeUnit.SECONDS)) {
			logger.info("await workerExecutorService stop...");
		}
		logger.info("workerExecutorService stoped.");
	}

}
