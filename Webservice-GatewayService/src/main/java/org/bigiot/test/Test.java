package org.bigiot.test;


import org.bigiot.service.RestHttpClient;


public class Test {
	public static void main(String[] args) {
		try {
//			WebserviceClient.findDevicesByIp("192.168.1.108");
//			WebserviceClient.getRecentValues("Temp","9c0b27c9e54b4db5aea08d889e38c346");
//			String aa = WebserviceClient.getHd("sss", "njuptcloud");
			 System.out.println(RestHttpClient.listAllDatas());
//			System.out.println(aa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
