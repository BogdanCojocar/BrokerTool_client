package com.brokerTool.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient {
	
	private final static String BROKER_TOOL_URI = "http://localhost:8080/BrokerTool/rest/";
	
	private static Client jerseyClient;
	private static WebResource resource;
	
	public static void init() {
		jerseyClient = Client.create();
		resource = jerseyClient.resource(BROKER_TOOL_URI);
	}

	public static WebResource getResource() {
		if (resource == null) {
			throw new NullPointerException("Jersey web resource is null.");
		}
		return resource;
	}
}
