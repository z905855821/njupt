package com.njupt.ws_cxf_spring.ws.bean;

public class Adapter {
	private int id;
	private String relationid;;
	private String openstate;
	private String savetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRelationid() {
		return relationid;
	}
	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}
	public String getOpenstate() {
		return openstate;
	}
	public void setOpenstate(String openstate) {
		this.openstate = openstate;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	
	public Adapter(int id, String relationid, String openstate, String savetime) {
		super();
		this.id = id;
		this.relationid = relationid;
		this.openstate = openstate;
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "Adapter [id=" + id + ", relationid=" + relationid + ", openstate=" + openstate + ", savetime="
				+ savetime + "]";
	}
	
}
