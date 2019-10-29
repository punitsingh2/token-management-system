package com.tms.service.producer;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.counter.service.IResourceService;
import com.tms.counter.service.ResourceServiceImpl;
import com.tms.domain.model.Resource;
import com.tms.resource.producer.ProducerCreator;
import com.tms.util.SystemConfiguration;
import com.tms.util.TokenGenerator;

public class NormalServiceProviderThread implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(NormalServiceProviderThread.class);

	private final BlockingQueue<Resource> queue;

	public NormalServiceProviderThread(final BlockingQueue<Resource> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		KafkaProducer<String, Resource> resourceProducer = (KafkaProducer<String, Resource>) NormalServiceProviderCreator
				.createProducer();
		try {
			while (true) {
				Resource take = queue.take();
				
				resourceProducer.send(new ProducerRecord<String, Resource>(
						SystemConfiguration.getInstance().getPropsValue("SERVICE_TOPIC"),
						"" + take.getToken().getTokenId(), take), new Callback() {

							public void onCompletion(RecordMetadata metadata, Exception exception) {

								if (exception == null) {
									LOGGER.info("Service Provider ==>"
											+ "Resource : {} \n Token Number : {} \n Waiting Number : {} \n"
											+ "Topic : {} \n Partition : {} \n Timestamp : {}",take.getResourceId(),take.getToken().getTokenId(),
											TokenGenerator.getInstance().awaitingNo(take),metadata.topic(),metadata.partition(),metadata.timestamp());
								} else {
									LOGGER.error("Error occures while messges producing", exception);
								}

							}
						});
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