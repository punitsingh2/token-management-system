package com.tms.main;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.domain.model.Resource;
import com.tms.resource.consumer.ResourceConsumerThread;
import com.tms.resource.producer.ResourceProducerThread;
import com.tms.service.consumer.NormalServiceConsumerThread;
import com.tms.service.premium.consumer.PremiumServiceConsumerThread;
import com.tms.service.premium.producer.PremiumServiceProviderThread;
import com.tms.service.producer.NormalServiceProviderThread;

/**
 * @author punsingh6 Main Entry class Customer will get in queue based on rush
 *         for document verification counter
 *
 */
public class TokenManagementSystem {

	private final static Logger LOGGER = LoggerFactory.getLogger(TokenManagementSystem.class);

	public static void main(String[] args) {
		
		

		
		// Start User Producer Thread
		ResourceProducerThread producerRunnable = new ResourceProducerThread();
	    Thread producerThread = new Thread(producerRunnable);
	    producerThread.start();
	    
	    BlockingQueue<Resource> normalResourceQueue = new LinkedBlockingQueue<>(10);
	    BlockingQueue<Resource> premiumResourceQueue = new LinkedBlockingQueue<>(10);
	 
	    // Start group of User Consumer Thread
	    ResourceConsumerThread consumerRunnable = new ResourceConsumerThread(normalResourceQueue,premiumResourceQueue);
	    Thread consumerThread = new Thread(consumerRunnable);
	    consumerThread.start();
	    
	    try {
		      Thread.sleep(1000);
		    } catch (InterruptedException ie) {
		 
		    }
	    
	    // start normal service thread
	    

	    NormalServiceProviderThread serviceProducerRunnable = new NormalServiceProviderThread(normalResourceQueue);
	    Thread serviceProducerThread = new Thread(serviceProducerRunnable);
	    serviceProducerThread.start();
	    
	    NormalServiceConsumerThread serviceConsumerRunnable = new NormalServiceConsumerThread();
	    Thread serviceConsumerThread = new Thread(serviceConsumerRunnable);
	    serviceConsumerThread.start();
	    
	    
	    try {
		      Thread.sleep(1000);
		    } catch (InterruptedException ie) {
		 
		    }
	    
	    
	    // start premium service thread
	    

	    PremiumServiceProviderThread premiumServiceProducerRunnable = new PremiumServiceProviderThread(premiumResourceQueue);
	    Thread premiumServiceProducerThread = new Thread(premiumServiceProducerRunnable);
	    premiumServiceProducerThread.start();
	    
	    PremiumServiceConsumerThread premimumServiceConsumerRunnable = new PremiumServiceConsumerThread();
	    Thread premiumServiceConsumerThread = new Thread(premimumServiceConsumerRunnable);
	    premiumServiceConsumerThread.start();
	    
	    
	    try {
		      Thread.sleep(1000);
		    } catch (InterruptedException ie) {
		 
		    }

        
       
	 
	    
		

	}

}
