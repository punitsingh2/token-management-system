package com.tms.counter.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tms.domain.model.Resource;
import com.tms.resource.type.ResourceTypeEnum;
import com.tms.resource.type.ServiceTypeEnum;
import com.tms.util.SystemConfiguration;

public class ResourceServiceImpl implements IResourceService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

	// Pairs of resource id and Resource object
	private static Map<String, Resource> resoucesMap = new HashMap<String, Resource>();

	@Override
	public Map<String, Resource> fetchAllResource() {
		String numberOfInputResource = SystemConfiguration.getInstance().getPropsValue("RESOURCE");
		String[] resources = numberOfInputResource.split(",");

		for (String val : resources) {
			int queueId = new Random()
					.nextInt(Stream.of(SystemConfiguration.getInstance().getPropsValue("COUNTER").split(","))
							.mapToInt(Integer::parseInt).toArray().length);
			
			Resource resource = new Resource("R" + val.split("~")[0],
					ResourceTypeEnum.valueOf(Integer.parseInt(val.split("~")[1])), queueId,
					ServiceTypeEnum.valueOf(Integer.parseInt(val.split("~")[2])));
			
			resoucesMap.put(resource.getResourceId(), resource);
			
			LOGGER.info("Resource {},  resource type {}, is queued in counter number {} for avail service {}.",
					resource.getResourceId(), resource.getResourceType(), resource.getQueueId(),
					resource.getServiceType());
		}
		return resoucesMap;
	}

	@Override
	public Resource getResource(final String resourceId) {

		return resoucesMap.get(resourceId);
	}

}
