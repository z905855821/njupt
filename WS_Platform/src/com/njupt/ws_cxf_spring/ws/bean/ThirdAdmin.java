package com.njupt.ws_cxf_spring.ws.bean;

public class ThirdAdmin {
	private int id;
	private String appkey;
	private String third_userkey;
	private String applystate;
	private String addtime;
	public ThirdAdmin(int id, String appkey, String third_userkey, String applystate, String addtime) {
		super();
		this.id = id;
		this.appkey = appkey;
		this.third_userkey = third_userkey;
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
	public String getThird_userkey() {
		return third_userkey;
	}
	public void setThird_userkey(String third_userkey) {
		this.third_userkey = third_userkey;
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
		return "ThirdAdmin [id=" + id + ", appkey=" + appkey + ", third_userkey=" + third_userkey + ", applystate="
				+ applystate + ", addtime=" + addtime + "]";
	}
	
}
