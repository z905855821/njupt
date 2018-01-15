package com.njupt.ws_cxf_spring.ws.test;

import java.util.ArrayList;
import java.util.Iterator;

import com.njupt.ws_cxf_spring.ws.bean.APPInfo;
import com.sun.org.apache.regexp.internal.recompile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test1 {
	public static void main(String[] args) {
		String json="[{\"deviceKey\":\"a41e7aec6ac24d68b35ff621fa9c1689\",\"dataType\":\"温度\",\"showType\":\"line\"},{\"deviceKey\":\"a41e7aec6ac24d68b35ff621fa9c1689\",\"dataType\":\"温度\",\"showType\":\"bar\"}]";
		ArrayList<String> list=jsonstring(json);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
	public static ArrayList<String> jsonstring(String json){
		ArrayList<String> list=new ArrayList<>();
		json="{\"content\":"+json+"}";
//		System.out.println(json);
		JSONObject object=JSONObject.fromObject(json);
		JSONArray jsonArray=object.getJSONArray("content");
		for(int i=0;i<jsonArray.size();i++){
			String string=jsonArray.getString(i);
//			System.out.println(string);
			JSONObject object1=JSONObject.fromObject(string);
			String devkey=object1.getString("deviceKey");
			if (!list.contains(devkey)) {
				list.add(devkey);
			}
		}
		return list;
	}
}
