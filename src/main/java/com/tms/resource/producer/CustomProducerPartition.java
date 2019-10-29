package com.tms.resource.producer;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.counter.service.IResourceService;
import com.tms.counter.service.ResourceServiceImpl;
import com.tms.domain.model.Resource;

public class CustomProducerPartition implements Partitioner {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomProducerPartition.class);

	private IResourceService resourceService;

	public CustomProducerPartition() {
		this.resourceService = new ResourceServiceImpl();
	}

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		int partition = 0;
		Integer queueId = Integer.parseInt(key.toString());

		// If the queueId not found, default partition is 0
		if (queueId != null) {
			partition = queueId;

		}
		LOGGER.info("Resource {} is queued at counter {}", ((Resource)value).getResourceId(), partition);
		return partition;

	}

	@Override
	public void close() {
	}

}
