package com.wanda.mxwkfk.core;

public class Cache {
	private static Cache instance = null;
	private boolean kafkaThreadStatus = true;

	public static Cache getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public synchronized static void init() {
		if (instance == null) {
			instance = new Cache();
		}
	}

	public boolean isKafkaThreadStatus() {
		return kafkaThreadStatus;
	}

	public void setKafkaThreadStatus(boolean kafkaThreadStatus) {
		this.kafkaThreadStatus = kafkaThreadStatus;
	}

}
