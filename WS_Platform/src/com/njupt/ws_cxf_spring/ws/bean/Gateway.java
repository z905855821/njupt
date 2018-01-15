package com.njupt.ws_cxf_spring.ws.bean;

public class Gateway {
	private String ip;
	private String mac;
	private String cpu_usr;
	private String cpu_sys;
	private String memory_total;
	private String memory_used;
	private String downlink;
	private String uplink;
	private String savetime;
	
	
	public Gateway(String ip, String mac, String cpu_usr, String cpu_sys, String memory_total, String memory_used,
			String downlink, String uplink, String savetime) {
		super();
		this.ip = ip;
		this.mac = mac;
		this.cpu_usr = cpu_usr;
		this.cpu_sys = cpu_sys;
		this.memory_total = memory_total;
		this.memory_used = memory_used;
		this.downlink = downlink;
		this.uplink = uplink;
		this.savetime = savetime;
	}

	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getCpu_usr() {
		return cpu_usr;
	}
	public void setCpu_usr(String cpu_usr) {
		this.cpu_usr = cpu_usr;
	}
	public String getCpu_sys() {
		return cpu_sys;
	}
	public void setCpu_sys(String cpu_sys) {
		this.cpu_sys = cpu_sys;
	}
	public String getMemory_total() {
		return memory_total;
	}
	public void setMemory_total(String memory_total) {
		this.memory_total = memory_total;
	}
	public String getMemory_used() {
		return memory_used;
	}
	public void setMemory_used(String memory_used) {
		this.memory_used = memory_used;
	}
	public String getDownlink() {
		return downlink;
	}
	public void setDownlink(String downlink) {
		this.downlink = downlink;
	}
	public String getUplink() {
		return uplink;
	}
	public void setUplink(String uplink) {
		this.uplink = uplink;
	}

	@Override
	public String toString() {
		return "Gateway [ip=" + ip + ", mac=" + mac + ", cpu_usr=" + cpu_usr + ", cpu_sys=" + cpu_sys
				+ ", memory_total=" + memory_total + ", memory_used=" + memory_used + ", downlink=" + downlink
				+ ", uplink=" + uplink + ", savetime=" + savetime + "]";
	}
	
	
}
