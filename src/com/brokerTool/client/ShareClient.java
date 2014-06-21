package com.brokerTool.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Share;
import com.brokerTool.service.Response;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ShareClient {
	
	private final static String SHARE_ID_URI = "share/%d";
	private final static String SHARE_UPDATE_URI = "share/update";
	private final static String SHARE_ADD_URI = "share/add";
	private final static String SHARE_DELETE_URI = "share/delete/%d";
	private final static String ALL_SHARES_URI = "share/all";
	private final static String ALL_SHARES_BY_BROKER_URI = "share/selectSharesByCustomerId/%d";
	
	private static WebResource resource = JerseyClient.getResource();
	
	public static Share getShareById(int id) {
		return resource.path(String.format(SHARE_ID_URI, id))
				.accept(MediaType.APPLICATION_XML).get(Share.class);
	}

	public static List<Share> getAllShares() {
		return resource.path(ALL_SHARES_URI)
				.accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Share>>() {
				});
	}

	public static Response addShare(Share share) {
		return resource.path(SHARE_ADD_URI)
				.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML).post(Response.class, share);
	}

	public static Response updateShare(Share share) {
		return resource.path(SHARE_UPDATE_URI)
				.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML).put(Response.class, share);
	}

	public static void deleteShareById(int id) {
		 resource.path(String.format(SHARE_DELETE_URI, id))
				.delete();
	}
	
	public static List<Share> selectSharesByCustomerId(int id) {
		return resource.path(String.format(ALL_SHARES_BY_BROKER_URI, id))
				.accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Share>>() {
				});
	}
}
