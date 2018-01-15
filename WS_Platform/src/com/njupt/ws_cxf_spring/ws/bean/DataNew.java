package com.njupt.ws_cxf_spring.ws.bean;

public class DataNew {
	int dataID;
	String value;
	String typeid;
	String devkey;
	String savetime;
	String location;
	String parentip;
	String mac;
	String type;
	
	public DataNew(int dataID, String value, String typeid, String devkey, String savetime, String location,
			String parentip, String mac, String type) {
		super();
		this.dataID = dataID;
		this.value = value;
		this.typeid = typeid;
		this.devkey = devkey;
		this.savetime = savetime;
		this.location = location;
		this.parentip = parentip;
		this.mac = mac;
		this.type = type;
	}
	public int getDataID() {
		return dataID;
	}
	public void setDataID(int dataID) {
		this.dataID = dataID;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public void setDevkey(String devkey) {
		this.devkey = devkey;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
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
	@Override
	public String toString() {
		return "DataNew [dataID=" + dataID + ", value=" + value + ", typeid=" + typeid + ", devkey=" + devkey
				+ ", savetime=" + savetime + ", location=" + location + ", parentip=" + parentip + ", mac=" + mac
				+ ", type=" + type + "]";
	}
	
}
