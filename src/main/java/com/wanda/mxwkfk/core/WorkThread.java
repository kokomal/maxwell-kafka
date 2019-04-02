package com.wanda.mxwkfk.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class WorkThread implements Callable<String> {

	private static Logger logger = LoggerFactory.getLogger(WorkThread.class);
	private String topic;
	private String message;
	private Semaphore semaphore;

	public WorkThread(String topic, String message, Semaphore semaphore) {
		this.topic = topic;
		this.message = message;
		this.semaphore = semaphore;
	}

	@Override
	public String call() throws Exception {
		try {
			semaphore.acquire();
			logger.info("topic is {}, message is {}", topic, message);
		} catch (Exception e) {
			logger.error("ParseKafkaLogJob run error. ", e);
		} finally {
			semaphore.release();
		}
		return "done";
	}

}
