package com.tms.service.premium.producer;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.counter.service.IResourceService;
import com.tms.counter.service.ResourceServiceImpl;
import com.tms.domain.model.Resource;
import com.tms.resource.type.ServiceTypeEnum;
import com.tms.service.producer.CustomeNormalServiceProviderPartition;

public class CustomePremiumServiceProviderPartition implements Partitioner {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomePremiumServiceProviderPartition.class);


	public CustomePremiumServiceProviderPartition() {
	}

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		int partition = 0;
		// provide service counter based on service required of resource
		Resource resource = (Resource) value;

		// If the resource not found, default partition is 0
		if (resource != null) {
			partition = ServiceTypeEnum.typeOf(resource.getServiceType());

		}
		LOGGER.info("Premium Resource {}, resource type {} for service {} will be queued at service counter {}",
				resource.getResourceId(), resource.getResourceType(), resource.getServiceType(), partition);
		return partition;

	}

	@Override
	public void close() {
	}

}
