package com.njupt.ws_cxf_spring.ws.bean;

public class Relation {
	private String ip;
	private String relationid;
	public Relation(String ip, String relationid) {
		super();
		this.ip = ip;
		this.relationid = relationid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRelationid() {
		return relationid;
	}
	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}
	@Override
	public String toString() {
		return "Relation [ip=" + ip + ", relationid=" + relationid + "]";
	}
	
}
