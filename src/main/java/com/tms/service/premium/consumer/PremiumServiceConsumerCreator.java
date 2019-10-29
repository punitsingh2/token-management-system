package com.tms.service.premium.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.tms.domain.model.Resource;
import com.tms.util.SystemConfiguration;

public class PremiumServiceConsumerCreator {
	
	public static KafkaConsumer<String, Resource> createConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("KAFKA_BROKERS"));
		props.put(ConsumerConfig.GROUP_ID_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("PREMIUM_SERVICE.GROUP_ID_CONFIG"));
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("CONSUMER.AUTO_COMMIT"));
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("CONSUMER.AUTO_COMMIT_INTERVAL"));
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("CONSUMER.SESSION_TIMEOUT"));
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("OFFSET_RESET_EARLIER"));
		// we need to define the key deserializer here
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				SystemConfiguration.getInstance().getPropsValue("RESOURCE_DESERIALIZER"));

		return new KafkaConsumer<String, Resource>(props);
	}


}
