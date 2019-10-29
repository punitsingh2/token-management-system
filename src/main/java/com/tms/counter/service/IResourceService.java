package com.tms.counter.service;

import java.util.List;
import java.util.Map;

import com.tms.domain.model.Resource;

public interface IResourceService {
	
	Resource getResource(final String resourceId);
	
	Map<String,Resource> fetchAllResource();

}
