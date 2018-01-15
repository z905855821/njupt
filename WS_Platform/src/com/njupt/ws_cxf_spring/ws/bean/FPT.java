package com.njupt.ws_cxf_spring.ws.bean;

public class FPT {
	private String frequency;
	private String dbm;
	private String time;
	
	public FPT(String frequency, String dbm, String time) {
		super();
		this.frequency = frequency;
		this.dbm = dbm;
		this.time = time;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDbm() {
		return dbm;
	}
	public void setDbm(String dbm) {
		this.dbm = dbm;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "FPT [frequency=" + frequency + ", dbm=" + dbm + ", time=" + time + "]";
	}
	
}
