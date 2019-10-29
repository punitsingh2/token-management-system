package com.tms.resource.producer;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.counter.service.IResourceService;
import com.tms.counter.service.ResourceServiceImpl;
import com.tms.domain.model.Resource;
import com.tms.main.TokenManagementSystem;
import com.tms.util.SystemConfiguration;

public class ResourceProducerThread implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResourceProducerThread.class);

	private IResourceService resourceService;

	public ResourceProducerThread() {
		this.resourceService = new ResourceServiceImpl();
	}

	@Override
	public void run() {
		Map<String, Resource> resourceMap = resourceService.fetchAllResource();
		KafkaProducer<String, Resource> resourceProducer = (KafkaProducer<String, Resource>) ProducerCreator
				.createProducer();
		for (Entry<String, Resource> resourceEntry : resourceMap.entrySet()) {

			resourceProducer.send(new ProducerRecord<String, Resource>(
					SystemConfiguration.getInstance().getPropsValue("COUNTER_TOPIC"),
					"" + resourceEntry.getValue().getQueueId(), resourceEntry.getValue()), new Callback() {

						public void onCompletion(RecordMetadata metadata, Exception exception) {

							if (exception == null) {
								LOGGER.info(
										"Resource Producer ==> \n" + "Recieved new meta data. \n Topic : {} \n"
												+ "Partition : {} \n Timestamp : {}",
										metadata.topic(), metadata.partition(), metadata.timestamp());
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

		// closes producer
		resourceProducer.flush();
		resourceProducer.close();

	}

}
