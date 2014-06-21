package com.brokerTool.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Stock;
import com.brokerTool.service.Response;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class StockClient {

	private final static String ALL_STOCKS_URI = "stock/all";
	private final static String STOCK_ADD_URI = "stock/add";

	private static WebResource resource = JerseyClient.getResource();

	public static List<Stock> getAllStocks() {
		return resource.path(ALL_STOCKS_URI).accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Stock>>() {
				});
	}
	
	public static Response addStock(Stock stock) {
		return resource.path(STOCK_ADD_URI)
				.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML).post(Response.class, stock);
	}
}
