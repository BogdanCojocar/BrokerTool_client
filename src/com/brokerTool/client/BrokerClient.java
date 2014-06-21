package com.brokerTool.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Broker;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class BrokerClient {
	
	private final static String ALL_BROKERS_URI = "broker/all";
	
	private static WebResource resource = JerseyClient.getResource();
	
	public static List<Broker> getAllBorkers() {
		return resource.path(ALL_BROKERS_URI).accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Broker>>() {
				});
	}
}
