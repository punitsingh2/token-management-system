package com.tms.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.tms.domain.model.Resource;
import com.tms.token.assignment.Token;
import com.tms.token.assignment.TokenId;

public class TokenGenerator {
	
	private static TokenGenerator tokenGenerator= null;

	public LinkedHashMap<String, LinkedList<Resource>> assignedTokensMaps;

	public Integer lastTokenNo = 0;
	
	private TokenGenerator() {
		assignedTokensMaps = new LinkedHashMap<String, LinkedList<Resource>>();
	}
	
	public static TokenGenerator getInstance() {
		if(tokenGenerator ==null) {
			synchronized (TokenGenerator.class) {
				if(tokenGenerator == null) {
					tokenGenerator = new TokenGenerator();
				}
			}
		}
		return tokenGenerator;
	}

	public boolean isEmpty() {
		return assignedTokensMaps.isEmpty();
	}

	public Token requestNewToken(final Resource resource) {
		TokenId newId = new TokenId(resource.getQueueId() + String.format("%02d", lastTokenNo++));
		Token token = new Token(newId);
		resource.setToken(token);

		if (assignedTokensMaps.isEmpty()) {
			LinkedList<Resource> resourceList = new LinkedList<Resource>();
			resourceList.add(resource);
			assignedTokensMaps.put(resource.getServiceType(), resourceList);
		} else {
			// first check if service already exits in map
			if (assignedTokensMaps.containsKey(resource.getServiceType())) {
				LinkedList<Resource> resourceList = assignedTokensMaps.get(resource.getServiceType());
				resourceList.add(resource);
				assignedTokensMaps.put(resource.getServiceType(), resourceList);
			} else {
				LinkedList<Resource> resourceList = new LinkedList<Resource>();
				resourceList.add(resource);
				assignedTokensMaps.put(resource.getServiceType(), resourceList);
			}

		}

		return token;
	}

	public int awaitingNo(final Resource resource) {
		if (assignedTokensMaps == null && assignedTokensMaps.isEmpty()) {
			return 0;
		}
		return assignedTokensMaps.get(resource.getServiceType()).size();
	}

	public Resource poll(final Resource resource) {
		return assignedTokensMaps.get(resource.getServiceType()).poll();
	}

	public Resource head(final Resource resource) {
		return assignedTokensMaps.get(resource.getServiceType()).size() > 0
				? assignedTokensMaps.get(resource.getServiceType()).getFirst()
				: null;
	}

	public Resource tail(final Resource resource) {
		return assignedTokensMaps.get(resource.getServiceType()).size() > 0
				? assignedTokensMaps.get(resource.getServiceType()).getLast()
				: null;
	}

}
