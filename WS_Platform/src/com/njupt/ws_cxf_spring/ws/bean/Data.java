package com.njupt.ws_cxf_spring.ws.bean;

public class Data {
	int dataID;
	String value;
	String type;
	String devKey;
	String saveTime;
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Data(int dataID, String value, String type, String devKey, String saveTime) {
		super();
		this.dataID = dataID;
		this.value = value;
		this.type = type;
		this.devKey = devKey;
		this.saveTime = saveTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevKey() {
		return devKey;
	}
	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	@Override
	public String toString() {
		return "Data [dataID=" + dataID + ", value=" + value + ", type=" + type + ", devKey=" + devKey + ", saveTime="
				+ saveTime + "]";
	}
	
}
