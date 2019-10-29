package com.tms.service.consumer;

import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.domain.model.Resource;
import com.tms.resource.consumer.ConsumerCreator;
import com.tms.util.SystemConfiguration;
import com.tms.util.TokenGenerator;

public class NormalServiceConsumerThread implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(NormalServiceConsumerThread.class);

	public NormalServiceConsumerThread() {
		// TODO Auto-generated constructor stub
	}

	public void run() {

		KafkaConsumer<String, Resource> consumer = (KafkaConsumer<String, Resource>) NormalServiceConsumerCreator.createConsumer();
		// subscribe to our consumer topics(s)
		consumer.subscribe(Arrays.asList(SystemConfiguration.getInstance().getPropsValue("SERVICE_TOPIC")));
		while (true) {
			ConsumerRecords<String, Resource> records = consumer.poll(Duration.ofMillis(100));
			for (final ConsumerRecord<String, Resource> record : records) {

				// Assign token and provide service based on resource required service
				Resource resource = (Resource) record.value();
				// now resouce has reached to destination
				LOGGER.info( "Serivce Consumer ==> \n"+
						"Request Type : {} \n Counter : {} \n Resource Id : {} \n Resource Type : {} \n "
						+ "Token Number : {} \n"
						+ "Partition : {} \n  OffSet : {} \n"
						+ "",
						resource.getServiceType(), resource.getQueueId(), resource.getResourceId(),
						resource.getResourceType(),resource.getToken().getTokenId(), record.partition(), record.offset());
				TokenGenerator.getInstance().poll(resource);

			}
		}

	}

}
