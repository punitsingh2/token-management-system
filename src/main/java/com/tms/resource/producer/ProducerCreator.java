package com.tms.resource.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import com.tms.domain.model.Resource;
import com.tms.util.SystemConfiguration;

public class ProducerCreator {

	public static KafkaProducer<String, Resource> createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("KAFKA_BROKERS"));
		// we need to define the key serializer here
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("RESOURCE_SERIALIZER"));
		// here we are using the partitioner we created
		props.put("partitioner.class", SystemConfiguration.getInstance().getPropsValue("RESOURCE_PARTITION"));

		return new KafkaProducer<String, Resource>(props);
	}

}
