package org.bigiot.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestHttpClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/springmvcdemo/light/getData";

	/* GET */
	@SuppressWarnings("unchecked")
	public static String listAllDatas()   {
		String result="[";
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI, List.class);
		ObjectMapper mapper = new ObjectMapper();
		if (usersMap != null) {
			for (LinkedHashMap<String, Object> map : usersMap) {
				String json = null;
				try {
					json = mapper.writeValueAsString(map);
					result+=json+",";
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //mapתjson  
		        
			}
		} else {
			System.out.println("No data exist----------");
		}
		result=result.substring(0,result.length()-1)+"]";
		return result;
	}


//	public static void main(String args[]) {
//		listAllDatas();
//	
//	}
}
