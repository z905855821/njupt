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

package org.bigiot.consumer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.bigiot.lib.Consumer;
import org.eclipse.bigiot.lib.exceptions.AccessToNonActivatedOfferingException;
import org.eclipse.bigiot.lib.exceptions.AccessToNonSubscribedOfferingException;
import org.eclipse.bigiot.lib.exceptions.IncompleteOfferingQueryException;
import org.eclipse.bigiot.lib.feed.AccessFeed;
import org.eclipse.bigiot.lib.model.BigIotTypes.LicenseType;
import org.eclipse.bigiot.lib.model.BigIotTypes.PricingModel;
import org.eclipse.bigiot.lib.model.Information;
import org.eclipse.bigiot.lib.model.RDFType;
import org.eclipse.bigiot.lib.model.ValueType;
import org.eclipse.bigiot.lib.model.Price.Euros;
import org.eclipse.bigiot.lib.offering.AccessParameters;
import org.eclipse.bigiot.lib.offering.AccessResponse;
import org.eclipse.bigiot.lib.offering.Offering;
import org.eclipse.bigiot.lib.offering.SubscribableOfferingDescription;
import org.eclipse.bigiot.lib.query.OfferingQuery;
import org.eclipse.bigiot.lib.query.elements.RegionFilter;
import org.joda.time.Duration;

public class ExampleConsumer1 {
	
	private static final String MARKETPLACE_URI = "https://market.big-iot.org";
	
	private static final String CONSUMER_ID	    = "Njupt-njuptconsumer";
	private static final String CONSUMER_SECRET = "CnvSikLYSTCZp3dM9nX_BQ==";
	
	public static void main(String args[]) throws InterruptedException, ExecutionException, IncompleteOfferingQueryException, IOException, AccessToNonSubscribedOfferingException, AccessToNonActivatedOfferingException {
	
		// Initialize consumer with Consumer ID and Marketplace URL
		Consumer consumer = new Consumer(CONSUMER_ID, MARKETPLACE_URI);
		
//		consumer.setProxy("127.0.0.1", 3128); //Enable this line if you are behind a proxy
//		consumer.addProxyBypass("172.17.17.100"); //Enable this line and the addresses for internal hosts
		
		// Authenticate consumer on the marketplace
		consumer.authenticate(CONSUMER_SECRET);
				
		Offering offering = consumer.subscribeByOfferingId("Njupt-njuptprovider-AirTemperatureOffering").get();
			// Prepare Access Parameters
			AccessParameters accessParameters = AccessParameters.create();
			
			// EXAMPLE 1: ONE-TIME ACCESS to the Offering
			/*AccessResponse response = offering.accessOneTime(accessParameters).get();
            System.out.println("Received data: " + response.asJsonNode().toString());*/
            
            // EXAMPLE 2: CONTINUOUS ACCESS to the Offering 
			// Create an Access Feed with callbacks for the received results		
			Duration feedDuration = Duration.standardHours(2);
			Duration feedInterval = Duration.standardSeconds(8);
			AccessFeed accessFeed = offering.accessContinuous(accessParameters, 
										feedDuration.getMillis(), 
										feedInterval.getMillis(), 
										(f,r) -> {
											System.out.println("Received data: " + r.asJsonNode().toString());
										},
										(f,r) -> {
											System.out.println("Feed operation failed");
											f.stop();
										});
			
			// Run until user presses the ENTER key
			System.out.println(">>>>>>  Terminate ExampleConsumer by pressing ENTER  <<<<<<");
			Scanner keyboard = new Scanner(System.in);
			keyboard.nextLine();
	
			// Stop Access Feed
			accessFeed.stop();
			
			// Unsubscribe the Offering
			offering.unsubscribe();

		
		// Terminate consumer instance
		consumer.terminate();
			
	}

}
