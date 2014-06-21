package com.brokerTool.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Customer;
import com.brokerTool.service.Response;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class CustomerClient {

	private final static String CUSTOMER_ID_URI = "customer/%d";
	private final static String CUSTOMER_UPDATE_URI = "customer/update";
	private final static String CUSTOMER_ADD_URI = "customer/add";
	private final static String CUSTOMER_DELETE_URI = "customer/delete/%d";
	private final static String ALL_CUSTOMERS_URI = "customer/all";
	private final static String ALL_CUSTOMERS_BY_BROKER_URI = "customer/selectCustomerByBrokerId/%d";

	private static WebResource resource = JerseyClient.getResource();

	public static void test() {
		System.out.println(resource.accept(MediaType.TEXT_PLAIN).get(
				String.class));
	}

	public static Customer getCustomerById(int id) {
		return resource.path(String.format(CUSTOMER_ID_URI, id))
				.accept(MediaType.APPLICATION_XML).get(Customer.class);
	}

	public static List<Customer> getAllCustomers() {
		return resource.path(ALL_CUSTOMERS_URI)
				.accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Customer>>() {
				});
	}

	public static Response addCustomer(Customer customer) {
		return resource.path(CUSTOMER_ADD_URI)
				.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML).post(Response.class, customer);
	}

	public static Response updateCustomer(Customer customer) {
		return resource.path(CUSTOMER_UPDATE_URI)
				.accept(MediaType.APPLICATION_XML)
				.type(MediaType.APPLICATION_XML).put(Response.class, customer);
	}

	public static void deleteCustomerById(int id) {
		resource.path(String.format(CUSTOMER_DELETE_URI, id)).delete();
	}

	public static List<Customer> selectCustomersByBrokerId(int id) {
		return resource.path(String.format(ALL_CUSTOMERS_BY_BROKER_URI, id))
				.accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Customer>>() {
				});
	}
}
