package com.tms.service.producer;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.counter.service.IResourceService;
import com.tms.counter.service.ResourceServiceImpl;
import com.tms.domain.model.Resource;
import com.tms.resource.producer.CustomProducerPartition;
import com.tms.resource.type.ServiceTypeEnum;

public class CustomeNormalServiceProviderPartition implements Partitioner {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomeNormalServiceProviderPartition.class);

	private IResourceService resourceService;

	public CustomeNormalServiceProviderPartition() {
		this.resourceService = new ResourceServiceImpl();
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
		LOGGER.info("Resource {}, resource type {} for service {} will be queued at service counter {}",
				resource.getResourceId(), resource.getResourceType(), resource.getServiceType(), partition);
		return partition;

	}

	@Override
	public void close() {
	}

}
