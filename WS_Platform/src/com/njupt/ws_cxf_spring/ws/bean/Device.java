package com.njupt.ws_cxf_spring.ws.bean;

public class Device {
	int deviceID;
	String devName;
	String devTag;
	String devDesc;
	String protocol;
	String devAuth;
	String location;
	String longitude;
	String latitude;
	int projectID;
	String deviceKey;
	public Device() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Device(int deviceID, String devName, String devTag, String devDesc, String protocol, String devAuth,
			String location, String longitude, String latitude, int projectID, String deviceKey) {
		super();
		this.deviceID = deviceID;
		this.devName = devName;
		this.devTag = devTag;
		this.devDesc = devDesc;
		this.protocol = protocol;
		this.devAuth = devAuth;
		this.location = location;
		this.longitude = longitude;
		this.latitude = latitude;
		this.projectID = projectID;
		this.deviceKey = deviceKey;
	}
	public int getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getDevTag() {
		return devTag;
	}
	public void setDevTag(String devTag) {
		this.devTag = devTag;
	}
	public String getDevDesc() {
		return devDesc;
	}
	public void setDevDesc(String devDesc) {
		this.devDesc = devDesc;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getDevAuth() {
		return devAuth;
	}
	public void setDevAuth(String devAuth) {
		this.devAuth = devAuth;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	@Override
	public String toString() {
		return "Device [deviceID=" + deviceID + ", devName=" + devName + ", devTag=" + devTag + ", devDesc=" + devDesc
				+ ", protocol=" + protocol + ", devAuth=" + devAuth + ", location=" + location + ", longitude="
				+ longitude + ", latitude=" + latitude + ", projectID=" + projectID + ", deviceKey=" + deviceKey + "]";
	}
	
}
