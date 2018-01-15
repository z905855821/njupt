package com.njupt.ws_cxf_spring.ws.bean;

public class HeartBean {
	int id;
	private String devkey;
    private String heartbeat;
    private String parentip;
    private String savetime;
	public HeartBean(int id, String devkey, String heartbeat, String parentip, String savetime) {
		super();
		this.id = id;
		this.devkey = devkey;
		this.heartbeat = heartbeat;
		this.parentip = parentip;
		this.savetime = savetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDevkey() {
		return devkey;
	}
	public void setDevkey(String devkey) {
		this.devkey = devkey;
	}
	public String getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(String heartbeat) {
		this.heartbeat = heartbeat;
	}
	public String getParentip() {
		return parentip;
	}
	public void setParentip(String parentip) {
		this.parentip = parentip;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "HeartBean [id=" + id + ", devkey=" + devkey + ", heartbeat=" + heartbeat + ", parentip=" + parentip
				+ ", savetime=" + savetime + "]";
	}
    
}
