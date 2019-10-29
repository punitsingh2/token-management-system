package com.tms.resource.producer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.domain.model.Resource;

public class ResourceSerializer implements Serializer<Resource> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ResourceSerializer.class);
	

	public byte[] serialize(String topic, Resource data) {
		byte[] retVal = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	      retVal = objectMapper.writeValueAsString(data).getBytes();
	    } catch (Exception e) {
	      LOGGER.error("Error occured while serializing the recource object",e);
	    }
	    return retVal;
		
	}


	
	
	

	  
}
