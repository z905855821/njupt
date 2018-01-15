package com.njupt.ws_cxf_spring.ws.bean;

public class AppOpen {
	private int id;
	private String appkey;
	private String userkey;
	private String applystate;
	private String addtime;
	public AppOpen(int id, String appkey, String userkey, String applystate, String addtime) {
		super();
		this.id = id;
		this.appkey = appkey;
		this.userkey = userkey;
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
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
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
		return "AppOpen [id=" + id + ", appkey=" + appkey + ", userkey=" + userkey + ", applystate=" + applystate
				+ ", addtime=" + addtime + "]";
	}
	
}
