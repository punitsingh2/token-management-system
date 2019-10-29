package com.tms.resource.consumer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.domain.model.Resource;

public class ResourceDeserializer implements Deserializer<Resource>{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ResourceDeserializer.class);

	@Override
	public Resource deserialize(String topic, byte[] data) {
		 ObjectMapper mapper = new ObjectMapper();
		    Resource resource = null;
		    try {
		    	resource = mapper.readValue(data, Resource.class);
		    } catch (Exception e) {

		     LOGGER.error("Error occured while deserializing : ",e);
		    }
		    return resource;
	}



	
}
