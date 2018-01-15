package com.njupt.ws_cxf_spring.ws.bean;

public class FrequencyDevice {
	private int id;
	private String frequency;
	private String dbm;
	private String typeid;
	private String devkey;
	private String location;
	private String parentip;
	private String mac;
	private String type;
	private String savetime;
	
	public FrequencyDevice(int id, String frequency, String dbm, String typeid, String devkey, String location,
			String parentip, String mac, String type, String savetime) {
		super();
		this.id = id;
		this.frequency = frequency;
		this.dbm = dbm;
		this.typeid = typeid;
		this.devkey = devkey;
		this.location = location;
		this.parentip = parentip;
		this.mac = mac;
		this.type = type;
		this.savetime = savetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getDevkey() {
		return devkey;
	}
	public void setDevkey(String devky) {
		this.devkey = devky;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getParentip() {
		return parentip;
	}
	public void setParentip(String parentip) {
		this.parentip = parentip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "FrequencyDevice [id=" + id + ", frequency=" + frequency + ", dbm=" + dbm + ", typeid=" + typeid
				+ ", devky=" + devkey + ", location=" + location + ", parentip=" + parentip + ", mac=" + mac + ", type="
				+ type + ", savetime=" + savetime + "]";
	}
	
	
}

