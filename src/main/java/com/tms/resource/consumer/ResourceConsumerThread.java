package com.tms.resource.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.domain.model.Resource;
import com.tms.resource.type.ResourceTypeEnum;
import com.tms.token.assignment.Token;
import com.tms.util.SystemConfiguration;
import com.tms.util.TokenGenerator;

public class ResourceConsumerThread implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResourceConsumerThread.class);

	private final BlockingQueue<Resource> normalResourceQueue;

	private final BlockingQueue<Resource> premiumResourceQueue;

	public ResourceConsumerThread(final BlockingQueue<Resource> pNormalResourceQueue,
			final BlockingQueue<Resource> pPremiumResourceQueue) {
		this.normalResourceQueue = pNormalResourceQueue;
		this.premiumResourceQueue = pPremiumResourceQueue;
	}

	public void run() {
		try {
			KafkaConsumer<String, Resource> consumer = (KafkaConsumer<String, Resource>) ConsumerCreator
					.createConsumer();
			// subscribe to our consumer topics(s)
			consumer.subscribe(Arrays.asList(SystemConfiguration.getInstance().getPropsValue("COUNTER_TOPIC")));
			while (true) {
				ConsumerRecords<String, Resource> records = consumer.poll(Duration.ofMillis(100));
				for (final ConsumerRecord<String, Resource> record : records) {

					// Assign token and provide service based on resource required service
					Resource resource = (Resource) record.value();
					// get token number
					Token token = TokenGenerator.getInstance().requestNewToken(resource);

					LOGGER.info("Resource Consumer ==> \n"
							+ "Request Type : {} \n Counter : {} \n Resource Id : {} \n Resource Type : {} \n Partition : {} \n  OffSet : {}",
							resource.getServiceType(), resource.getQueueId(), resource.getResourceId(),
							resource.getResourceType(), record.partition(), record.offset());

					// assign to resource
					resource.setToken(token);
					// now put in BlockingQueue for consuming the normal type resource
					if ("NORMAL".equals(resource.getResourceType().trim())) {
						normalResourceQueue.put(resource);
					} else {
						// here for premium service
						premiumResourceQueue.put(resource);
					}

				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
