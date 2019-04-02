package com.wanda.mxwkfk.model;

import java.io.Serializable;

public class MaxwellDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String database;
	private String table;
	private String type;
	private Long ts;
	private long xid;
	private Boolean commit;
	private String data;

	/* =====Getters and Setters below===== */
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public long getXid() {
		return xid;
	}

	public void setXid(long xid) {
		this.xid = xid;
	}

	public Boolean getCommit() {
		return commit;
	}

	public void setCommit(Boolean commit) {
		this.commit = commit;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "MaxwellDto [database=" + database + ", table=" + table + ", type=" + type + ", ts=" + ts + ", xid="
				+ xid + ", commit=" + commit + ", data=" + data + "]";
	}

}
