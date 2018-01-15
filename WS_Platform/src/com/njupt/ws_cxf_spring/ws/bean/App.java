package com.njupt.ws_cxf_spring.ws.bean;

public class App {
	int id;
	String appName;
	String appInfo;
	String appKey;
	int projectID;
	String saveTime;
	public App() {
		super();
		// TODO Auto-generated constructor stub
	}
	public App(int id, String appName, String appInfo, String appKey, int projectID, String saveTime) {
		super();
		this.id = id;
		this.appName = appName;
		this.appInfo = appInfo;
		this.appKey = appKey;
		this.projectID = projectID;
		this.saveTime = saveTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	@Override
	public String toString() {
		return "App [id=" + id + ", appName=" + appName + ", appInfo=" + appInfo + ", appKey=" + appKey + ", projectID="
				+ projectID + ", saveTime=" + saveTime + "]";
	}
	
}
