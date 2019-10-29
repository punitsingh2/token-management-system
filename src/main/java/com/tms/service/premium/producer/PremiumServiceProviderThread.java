package com.tms.service.premium.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.domain.model.Resource;
import com.tms.service.producer.NormalServiceProviderCreator;
import com.tms.util.SystemConfiguration;
import com.tms.util.TokenGenerator;

public class PremiumServiceProviderThread implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(PremiumServiceProviderThread.class);

	private final BlockingQueue<Resource> premimumResourceQueue;

	public PremiumServiceProviderThread(final BlockingQueue<Resource> queue) {
		this.premimumResourceQueue = queue;
	}

	@Override
	public void run() {

		KafkaProducer<String, Resource> resourceProducer = (KafkaProducer<String, Resource>) NormalServiceProviderCreator
				.createProducer();
		try {
			while (true) {
				Resource take = premimumResourceQueue.take();
				
				try {
					resourceProducer.send(new ProducerRecord<String, Resource>(
							SystemConfiguration.getInstance().getPropsValue("PREMIUM_SERVICE_TOPIC"),
							"" + take.getToken().getTokenId(), take), new Callback() {

								public void onCompletion(RecordMetadata metadata, Exception exception) {

									if (exception == null) {
										LOGGER.info("Premium Service Provider ==>"
												+ "Resource : {} \n Token Number : {} \n Waiting Number : {} \n"
												+ "Topic : {} \n Partition : {} \n Timestamp : {}",take.getResourceId(),take.getToken().getTokenId(),
												TokenGenerator.getInstance().awaitingNo(take),metadata.topic(),metadata.partition(),metadata.timestamp());
									} else {
										LOGGER.error("Error occures while messges producing", exception);
									}

								}
							}).get();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					LOGGER.error("Error occured", e);
				}

			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		// closes producer
		resourceProducer.flush();
		resourceProducer.close();

	}

}
