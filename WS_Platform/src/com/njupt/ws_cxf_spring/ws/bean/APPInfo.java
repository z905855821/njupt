package com.njupt.ws_cxf_spring.ws.bean;

public class APPInfo {
	private String devkey;
	private String datatype;
	private String showtype;
	
	public APPInfo(String devkey, String datatype, String showtype) {
		super();
		this.devkey = devkey;
		this.datatype = datatype;
		this.showtype = showtype;
	}
	public String getDevkey() {
		return devkey;
	}
	public void setDevkey(String devkey) {
		this.devkey = devkey;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getShowtype() {
		return showtype;
	}
	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}
	@Override
	public String toString() {
		return "APPInfo [devkey=" + devkey + ", datatype=" + datatype + ", showtype=" + showtype + "]";
	}
	
}
