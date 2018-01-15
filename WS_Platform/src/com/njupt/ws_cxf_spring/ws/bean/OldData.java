package com.njupt.ws_cxf_spring.ws.bean;

public class OldData {
	private int deviceid;
	private String name;
	private String value;
	private String location;
	private String time;
	public OldData(int deviceid, String name, String value, String location, String time) {
		super();
		this.deviceid = deviceid;
		this.name = name;
		this.value = value;
		this.location = location;
		this.time = time;
	}
	public int getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "OldData [deviceid=" + deviceid + ", name=" + name + ", value=" + value + ", location=" + location
				+ ", time=" + time + "]";
	}
	
}
