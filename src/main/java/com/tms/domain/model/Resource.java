package com.tms.domain.model;

import com.tms.token.assignment.Token;

public class Resource {

	/**
	 * resource
	 */
	private String resourceId;

	/**
	 * Counter Number assigned to resource
	 */
	private Integer queueId;

	/**
	 * Token number assigned to resource
	 */
	private Token token;

	/**
	 * resource can be normal or premium type
	 */
	private String resourceType;

	/**
	 * Service type to be avail
	 */
	private String serviceType;

	public Resource() {
		// TODO Auto-generated constructor stub
	}

	public Resource(final String pResourceId, final String pResourceType, final int pQueueId,
			final String pServiceType) {
		this.resourceId = pResourceId;
		this.resourceType = pResourceType;
		this.queueId = pQueueId;
		this.serviceType = pServiceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return "Resource(" + resourceId + ", " + queueId + ", " + token + ", " + resourceType + "," + serviceType + ")";
	}

}
