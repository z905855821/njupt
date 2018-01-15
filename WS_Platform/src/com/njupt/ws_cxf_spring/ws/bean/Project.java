package com.njupt.ws_cxf_spring.ws.bean;

public class Project {
	int projectID;
	String projectName;
	String ownerApikey;
	String projectKey;
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(int projectID, String projectName, String ownerApikey, String projectKey) {
		super();
		this.projectID = projectID;
		this.projectName = projectName;
		this.ownerApikey = ownerApikey;
		this.projectKey = projectKey;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOwnerApikey() {
		return ownerApikey;
	}
	public void setOwnerApikey(String ownerApikey) {
		this.ownerApikey = ownerApikey;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	@Override
	public String toString() {
		return "Projects [projectID=" + projectID + ", projectName=" + projectName + ", ownerApikey=" + ownerApikey
				+ ", projectKey=" + projectKey + "]";
	}
	
}
