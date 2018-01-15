package com.njupt.ws_cxf_spring.ws.bean;

public class Config {
	private int id;
	private String devkey;
	private String configtime;
	private String content;
	public Config(int id, String devkey, String configtime, String content) {
		super();
		this.id = id;
		this.devkey = devkey;
		this.configtime = configtime;
		this.content = content;
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
	public String getConfigtime() {
		return configtime;
	}
	public void setConfigtime(String configtime) {
		this.configtime = configtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Config [id=" + id + ", devkey=" + devkey + ", configtime=" + configtime + ", content=" + content + "]";
	}
	
}
