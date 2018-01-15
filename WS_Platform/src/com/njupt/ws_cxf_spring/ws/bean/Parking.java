package com.njupt.ws_cxf_spring.ws.bean;



public class Parking {
	private int id;
	private int deviceid;
	private String value;
	private String savetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	public Parking(int id, int deviceid, String value, String savetime) {
		super();
		this.id = id;
		this.deviceid = deviceid;
		this.value = value;
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "Parking [id=" + id + ", deviceid=" + deviceid + ", value=" + value + ", savetime=" + savetime + "]";
	}
	
}
