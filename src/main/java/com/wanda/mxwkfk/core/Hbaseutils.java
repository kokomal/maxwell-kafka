package com.wanda.mxwkfk.core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import java.io.IOException;

public class Hbaseutils {
	public static Connection getconnection(String zooklist, String port) {
		Connection conn = null;
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", zooklist);
		conf.set("hbase.zookeeper.property.clientPort", port);
		try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
