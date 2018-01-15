/*
 *	Copyright (c) 2017 by Contributors of the BIG IoT Project Consortium (see below).
 *	All rights reserved. 
 *		
 *	This source code is licensed under the MIT license found in the
 * 	LICENSE file in the root directory of this source tree.
 *		
 *	Contributor:
 *	- Robert Bosch GmbH 
 *	    > Stefan Schmid (stefan.schmid@bosch.com)
 */

package org.bigiot.provider;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.bigiot.service.WebserviceClient;
import org.eclipse.bigiot.lib.ProviderSpark;
import org.eclipse.bigiot.lib.exceptions.IncompleteOfferingDescriptionException;
import org.eclipse.bigiot.lib.exceptions.NotRegisteredException;
import org.eclipse.bigiot.lib.handlers.AccessRequestHandler;
import org.eclipse.bigiot.lib.model.BigIotTypes.LicenseType;
import org.eclipse.bigiot.lib.model.BigIotTypes.PricingModel;
import org.eclipse.bigiot.lib.model.Information;
import org.eclipse.bigiot.lib.model.Price.Euros;
import org.eclipse.bigiot.lib.model.RDFType;
import org.eclipse.bigiot.lib.model.ValueType;
import org.eclipse.bigiot.lib.offering.OfferingDescription;
import org.eclipse.bigiot.lib.offering.RegisteredOffering;
import org.eclipse.bigiot.lib.offering.RegistrableOfferingDescription;
import org.eclipse.bigiot.lib.query.elements.RegionFilter;
import org.eclipse.bigiot.lib.serverwrapper.BigIotHttpResponse;

import net.sf.json.JSONArray;


/** 
 * Example for using BIG IoT API as a Provider. This example corresponds with Example Consumer project * 
 */
public class ExampleProvider1 {
	
	private static final String MARKETPLACE_URI = "https://market.big-iot.org";
	
	private static final String PROVIDER_ID 	= "Njupt-njuptprovider";
	private static final String PROVIDER_SECRET = "3HtWMX18T66kTEl9SByc4w==";
	
//	private static Random rand = new Random();

	private static AccessRequestHandler accessCallback = new AccessRequestHandler() {
		@Override
		public BigIotHttpResponse processRequestHandler (
	           OfferingDescription offeringDescription, Map<String,Object> inputData, String subscriberId, String consumerInfo) {
			
			/*
			double longitude=0, latitude=0, radius=0;
			if (inputData.containsKey("longitude")) {
				longitude = new Double(inputData.get("longitude"));
			}
			if (inputData.containsKey("latitude")) {
				latitude = new Double(inputData.get("latitude"));
			}
			if (inputData.containsKey("radius")) {
				radius = new Double(inputData.get("radius"));
			}
			*/
			
		    // Prepare the offering response as a JSONObject/Array - according to the Output Data defined in the Offering Description
//		    JSONObject number = new JSONObject();
//		    number.put("value", rand.nextFloat());
//		    number.put("id", ""+rand.nextInt());
//		    number.put("typeid", "Temp");
//		    number.put("devkey", ""+UUID.randomUUID());
//		    number.put("savetime", "2017/12/23 15:32");
//		    number.put("location", "物联网科技园5楼");
//		    number.put("parentip", "192.168.0.108");
//		    number.put("mac", "5C:CF:7F:85:FA:98");
//		    number.put("type", "wifi");
//		    number.put("timestamp", new Date().getTime());
			String s="";
			try {
				 s=WebserviceClient.getRecentValues("Temp","9c0b27c9e54b4db5aea08d889e38c346");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // Send the response as JSON in the form: { [ { "value" : 0.XXX, "timestamp" : YYYYYYY } ] }
//			return BigIotHttpResponse.okay().withBody(number).asJsonType();
			 return BigIotHttpResponse.okay().withBody(s);
		}
	};

	public static void main(String args[]) throws InterruptedException, IncompleteOfferingDescriptionException, IOException, NotRegisteredException {
		// Initialize provider with Provider ID and Marketplace URI
		ProviderSpark provider = new ProviderSpark(PROVIDER_ID, MARKETPLACE_URI, "localhost", 9021);
		
		// Authenticate provider instance on the marketplace 
	    provider.authenticate(PROVIDER_SECRET);
	 	    
	    // Construct Offering Description of your Offering incrementally
	    RegistrableOfferingDescription offeringDescription = provider.createOfferingDescription("AirTemperatureOffering")
	    		.withInformation(new Information ("Nanjing AirTemperature", new RDFType("bigiot:AirTemperature")))
	    		//.addInputData("longitude", new RDFType("schema:longitude"))
	    		//.addInputData("latitude", new RDFType("schema:latitude"))
	    		//.addInputData("radius", new RDFType("schema:geoRadius"))
	    		.addOutputData("value", new RDFType("bigiot:Value"), ValueType.NUMBER)
	    		.addOutputData("id", new RDFType("bigiot:Id"), ValueType.TEXT)
	    		.addOutputData("typeid", new RDFType("bigiot:SensorType"), ValueType.TEXT)
	    		.addOutputData("devKey", new RDFType("bigiot:DeviceKey"), ValueType.TEXT)
	    		.addOutputData("savetime", new RDFType("bigiot:Savetime"), ValueType.TEXT)
	    		.addOutputData("location", new RDFType("bigiot:Location"), ValueType.TEXT)
	    		.addOutputData("parentip", new RDFType("bigiot:Parentip"), ValueType.TEXT)
	    		.addOutputData("mac", new RDFType("bigiot:Mac"), ValueType.TEXT)
	    		.addOutputData("type", new RDFType("bigiot:Protocol"), ValueType.TEXT)
//	            .addOutputData("timestamp", new RDFType("schema:datePublished"), ValueType.NUMBER)
	    		.inRegion(RegionFilter.city("Nanjing"))
//	    		.withPrice(Euros.amount(0.001))
	    		.withPricingModel(PricingModel.FREE)
	    		.withLicenseType(LicenseType.OPEN_DATA_LICENSE)
	    		//Below is actually Offering specific	
//	    		.withRoute("randomvalue")
	    		.withAccessRequestHandler(accessCallback);
	    
	    // Register OfferingDescription on Marketplace - this will create a local endpoint based on the embedded Spark Web server
	    RegisteredOffering offering = offeringDescription.register();
	    
		// Run until user presses the ENTER key
		System.out.println(">>>>>>  Terminate ExampleProvider by pressing ENTER  <<<<<<");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
	 
		System.out.println("Deregister Offering");

		// Deregister the Offering from the Marketplace
	    offering.deregister();
	    
	    // Terminate the Provider instance 
	    provider.terminate();
	   
	}

}

