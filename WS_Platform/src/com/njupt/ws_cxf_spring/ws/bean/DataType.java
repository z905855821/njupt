package com.njupt.ws_cxf_spring.ws.bean;

import java.util.Date;

public class DataType {

	int dataTypeID;
	String type;
	String symbol;
	int showType;
	String createtime;
	String devKey;
	public DataType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataType(int dataTypeID, String type, String symbol, int showType, String createtime, String devKey) {
		super();
		this.dataTypeID = dataTypeID;
		this.type = type;
		this.symbol = symbol;
		this.showType = showType;
		this.createtime = createtime;
		this.devKey = devKey;
	}
	public int getDataTypeID() {
		return dataTypeID;
	}
	public void setDataTypeID(int dataTypeID) {
		this.dataTypeID = dataTypeID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDevKey() {
		return devKey;
	}
	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}
	@Override
	public String toString() {
		return "DataType [dataTypeID=" + dataTypeID + ", type=" + type + ", symbol=" + symbol + ", showType=" + showType
				+ ", createtime=" + createtime + ", devKey=" + devKey + "]";
	}
	
	
}
