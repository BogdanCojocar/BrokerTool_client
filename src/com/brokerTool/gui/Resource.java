package com.brokerTool.gui;

import com.brokerTool.model.Broker;
import com.brokerTool.model.Customer;
import com.brokerTool.model.Share;
import com.brokerTool.model.Stock;

public class Resource {
	
	private final static Resource instance = new Resource();
	private Broker broker = new Broker();
	private Share share = new Share();
	private Stock stock = new Stock();
	private Customer customer = new Customer();
	private Boolean calledFromBrokerView = false;
	private Boolean calledFromSharesView = false;
	
	private Resource() {}
	
	public static Resource getinstance() {
		return instance;
	}
	
	public Broker getBroker() {
		return broker;
	}
	
	public void setBroker(Broker broker) {
		this.broker = broker;
	}
	
	public Share getShare() {
		return share;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getCalledFromBrokerView() {
		return calledFromBrokerView;
	}

	public void setCalledFromBrokerView(Boolean calledFromBrokerView) {
		this.calledFromBrokerView = calledFromBrokerView;
	}

	public Boolean getCalledFromSharesView() {
		return calledFromSharesView;
	}

	public void setCalledFromSharesView(Boolean calledFromSharesView) {
		this.calledFromSharesView = calledFromSharesView;
	}
}
