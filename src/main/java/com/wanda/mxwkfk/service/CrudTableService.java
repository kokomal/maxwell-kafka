package com.wanda.mxwkfk.service;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSONObject;
import com.wanda.mxwkfk.core.Hbaseutils;
import com.wanda.mxwkfk.model.MaxwellDto;
import com.wanda.mxwkfk.model.biz.IMDto;

public class CrudTableService {
	protected static final Log log = LogFactory.getLog(CrudTableService.class);
	public static Connection conns = null;

	public CrudTableService(String zookeeperlist, String port) {
		conns = Hbaseutils.getconnection(zookeeperlist, port);
	}

	public static boolean createImageScanTable(String table) {
		try {
			Admin admin = conns.getAdmin();
			if (!admin.tableExists(TableName.valueOf(table))) {
				HTableDescriptor tbdesc = new HTableDescriptor(TableName.valueOf(table));
				tbdesc.addFamily(new HColumnDescriptor("content"));
				admin.createTable(tbdesc);
			}
			admin.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static boolean insertImageScanRecord(String table, String rowid, String id, String name) {
		try {
			Put put = new Put(Bytes.toBytes(rowid));
			put.addColumn(Bytes.toBytes("content"), Bytes.toBytes("id"), Bytes.toBytes(id));
			put.addColumn(Bytes.toBytes("content"), Bytes.toBytes("name"), Bytes.toBytes(name));
			Table tb = conns.getTable(TableName.valueOf(table));
			tb.put(put);
			tb.close();
			return true;
		} catch (IOException e) {
			log.error(e);
			return false;
		}

	}

	public static String rowkeys(String str) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			buffer.append(str.charAt(new Random().nextInt(str.length())));
		}
		return buffer.toString();
	}

	public void parseRawJson(String json) {
		System.out.println(json);
		MaxwellDto dto = JSONObject.parseObject(json, MaxwellDto.class);
		String database = dto.getDatabase();
		String tablename = dto.getTable();
		log.info("dto:" + dto);
		log.info("tablename" + tablename);
		log.info("type" + dto.getType());
		if (dto.getDatabase().equals("canal_test") && dto.getType().equals("insert")) {
			IMDto im = JSONObject.parseObject(dto.getData(), IMDto.class);
			System.out.println("wwwwwwwwwwwwwww" + im);
			if (!createImageScanTable(tablename)) {
				System.out.println("check or create image scan table(" + tablename + ") error  ");
				return;
			}
			String rowid = rowkeys("abcdefg012345");
			String id;
			String name;
			id = im.getId();
			name = im.getName();
			if (id == null) {
				id = "null";
			}
			if (name == null) {
				name = "null";
			}
			if (!insertImageScanRecord(tablename, rowid, id, new String(name))) {
				System.out.println("insert image scan record errortable: " + tablename);
				return;
			}
			System.out.println(id + name + " is imported");
		}

	}

}
