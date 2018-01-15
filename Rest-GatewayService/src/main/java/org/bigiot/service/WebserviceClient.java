package org.bigiot.service;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebserviceClient {
	 static final String SERVICE_NAMESPACE = "http://ws.ws_cxf_spring.njupt.com/";
	 static final String SERVICE_URL = "http://202.119.234.4/WS_Platform/platformws?wsdl";
	 private static String handle = null;	 
	 static {
		 String methodName = "getPermission";
			SoapObject request = new SoapObject(SERVICE_NAMESPACE, methodName);
			request.addProperty("arg0","sss"); 
			request.addProperty("arg1","njuptcloud");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = request;
			//envelope.dotNet=true; 	
			(new MarshalBase64()).register(envelope);  
			envelope.encodingStyle="UTF-8";
			HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
//			AndroidHttpTransport ht = new AndroidHttpTransport(SERVICE_URL);	 	
			ht.debug=true;
			try {
				ht.call(null, envelope);
			} catch (IOException e) {
				e.printStackTrace();
				
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} 
			SoapObject  result = (SoapObject) envelope.bodyIn;   
			handle=result.getProperty(0).toString();
			
		}
	public static  String getHd(String arg0,String arg1) throws Exception{
		
		String methodName = "getPermission";
		SoapObject request = new SoapObject(SERVICE_NAMESPACE, methodName);
		request.addProperty("arg0",arg0); 
		request.addProperty("arg1",arg1);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = request;
		//envelope.dotNet=true; 	
		(new MarshalBase64()).register(envelope);  
		envelope.encodingStyle="UTF-8";
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
//		AndroidHttpTransport ht = new AndroidHttpTransport(SERVICE_URL);	 	
		ht.debug=true;
		try {
			ht.call(null, envelope);
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} 
		SoapObject  result = (SoapObject) envelope.bodyIn;   
		String m=result.getProperty(0).toString();
		return m;

	}	
	/**
	 * 获取最新的数据的值
	 * @param type
	 * @param devkey
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentValue(String type, String devkey)throws Exception {
		String methodName = "getCurrentValue";
		SoapObject request = new SoapObject(SERVICE_NAMESPACE, methodName);
		request.addProperty("arg0",handle); 
		request.addProperty("arg1",type); 
		request.addProperty("arg2",devkey); 
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = request;
		//envelope.dotNet=true; 	
		(new MarshalBase64()).register(envelope);  
		envelope.encodingStyle="UTF-8";
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);	 	
		ht.debug=true;
		try {
			ht.call(null, envelope);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} 
		SoapObject  result = (SoapObject) envelope.bodyIn;   
		String m=result.getProperty(0).toString();
		System.out.println(m);
		return m;
	}
	/**
	 * 获得最新8条数据
	 * @param type
	 * @param devkey
	 * @return
	 * @throws Exception
	 */
	public static String getRecentValues(String type, String devkey)throws Exception {
		String methodName = "getRecentValues";
		SoapObject request = new SoapObject(SERVICE_NAMESPACE, methodName);
		request.addProperty("arg0",handle); 
		request.addProperty("arg1",type); 
		request.addProperty("arg2",devkey); 
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = request;
		//envelope.dotNet=true; 	
		(new MarshalBase64()).register(envelope);  
		envelope.encodingStyle="UTF-8";
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);	 	
		ht.debug=true;
		try {
			ht.call(null, envelope);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} 
		SoapObject  result = (SoapObject) envelope.bodyIn;   
		String m=result.getProperty(0).toString();
		System.out.println(m);
		return m;
	}
	
	
}
