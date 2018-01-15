package com.njupt.ws_cxf_spring.ws.bean;

public class DeviceOpen {
	private int id;
	private String appkey;
	private String devkey;
	private String applystate;
	private String addtime;
	public DeviceOpen(int id, String appkey, String devkey, String applystate, String addtime) {
		super();
		this.id = id;
		this.appkey = appkey;
		this.devkey = devkey;
		this.applystate = applystate;
		this.addtime = addtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getDevkey() {
		return devkey;
	}
	public void setDevkey(String devkey) {
		this.devkey = devkey;
	}
	public String getApplystate() {
		return applystate;
	}
	public void setApplystate(String applystate) {
		this.applystate = applystate;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "DeviceOpen [id=" + id + ", appkey=" + appkey + ", devkey=" + devkey + ", applystate=" + applystate
				+ ", addtime=" + addtime + "]";
	}
	
}
