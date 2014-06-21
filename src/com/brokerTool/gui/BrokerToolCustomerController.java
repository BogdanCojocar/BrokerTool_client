package com.brokerTool.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.brokerTool.client.CustomerClient;
import com.brokerTool.gui.BrokerToolFXML.PaneType;
import com.brokerTool.model.Customer;
import com.brokerTool.service.Response;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BrokerToolCustomerController implements Initializable {

	@FXML
	private TableView<CustomerBean> customerTable;
	@FXML
	private TableColumn<CustomerBean, String> customer_id;
	@FXML
	private TableColumn<CustomerBean, String> account;
	@FXML
	private TableColumn<CustomerBean, String> fullname;
	@FXML
	private TableColumn<CustomerBean, String> address;
	@FXML
	private TextField account_field;
	@FXML
	private TextField address_field;
	@FXML
	private TextField fullname_field;
	@FXML
	static Pane customerPane;
	private ArrayList<Customer> customers;
	private int lastSelectedIndex;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lastSelectedIndex = -1;
		updateTable();
	}

	private void updateTable() {
		customers = (ArrayList<Customer>) CustomerClient
				.selectCustomersByBrokerId(Resource.getinstance().getBroker()
						.getBrokerId());
		if (customers != null) {
			customerTable.getItems().clear();
			for (Customer customer : customers) {
				addNewColumn(customer);
			}
			customer_id
					.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>(
							"customer_id"));
			account.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>(
					"account"));
			fullname.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>(
					"fullname"));
			address.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>(
					"address"));
		}
	}

	private void addNewColumn(Customer customer) {
		customerTable.getItems().add(
				new CustomerBean(customer.getCustomerId().toString(), customer
						.getAccount(), customer.getFullname(), customer
						.getAddress()));
	}

	@FXML
	public void showShares(ActionEvent event) {
		Resource.getinstance().getShare().setSharesId(lastSelectedIndex + 1);
		Resource.getinstance().setCustomer(customers.get(lastSelectedIndex));
		BrokerToolFXML
				.makeTransition(BrokerToolFXML.BROKER_TOOL_SHARE_VIEW_NAME,
						PaneType.SHARE_PANE);
	}

	@FXML
	public void handleBackButton(ActionEvent event) {
		BrokerToolFXML.makeTransition(BrokerToolFXML.BROKER_TOOL_VIEW_NAME,
				PaneType.BROKER_PANE);
	}

	@FXML
	public void handleSelectButton(ActionEvent event) {
		if (lastSelectedIndex != -1) {
			Customer customer = customers.get(lastSelectedIndex);
			account_field.setText(customer.getAccount());
			fullname_field.setText(customer.getFullname());
			address_field.setText(customer.getAddress());
		}
	}

	@FXML
	public void handleUpdateButton(ActionEvent event) {
		if (lastSelectedIndex != -1) {
			Customer customer = CustomerClient
					.getCustomerById(lastSelectedIndex + 1);
			customer.setAccount(account_field.getText());
			customer.setFullname(fullname_field.getText());
			Response resp = CustomerClient.updateCustomer(customer);
			System.out.println(resp.toString());
			// update table
			updateTable();
			cleanTextField();
		}
	}

	@FXML
	public void handleAdd(ActionEvent event) {
		Customer newCustomer = new Customer();
		newCustomer.setCustomerId(customers.size() + 1);
		newCustomer.setAccount(account_field.getText());
		newCustomer.setFullname(fullname_field.getText());
		newCustomer.setAddress(address_field.getText());
		newCustomer.setBroker(Resource.getinstance().getBroker());
		addNewColumn(newCustomer);
		Response resp = CustomerClient.addCustomer(newCustomer);
		System.out.println(resp.toString());
		cleanTextField();
	}

	@FXML
	public void handleDelete(ActionEvent event) {
		if (lastSelectedIndex != -1) {
			customerTable.getItems().remove(lastSelectedIndex);
			CustomerClient.deleteCustomerById(lastSelectedIndex + 1);
		}
	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		int currentIndex = customerTable.getSelectionModel().getSelectedIndex();
		if (lastSelectedIndex == -1 || lastSelectedIndex != currentIndex) {
			lastSelectedIndex = currentIndex;
		}
	}

	private void cleanTextField() {
		account_field.setText("");
		fullname_field.setText("");
		address_field.setText("");
	}

	public static class CustomerBean {
		private final SimpleStringProperty customer_id;
		private final SimpleStringProperty account;
		private final SimpleStringProperty fullname;
		private final SimpleStringProperty address;

		private CustomerBean(String customer_id, String account,
				String fullname, String address) {
			this.customer_id = new SimpleStringProperty(customer_id);
			this.account = new SimpleStringProperty(account);
			this.fullname = new SimpleStringProperty(fullname);
			this.address = new SimpleStringProperty(address);
		}

		public String getCustomer_id() {
			return customer_id.get();
		}

		public String getAccount() {
			return account.get();
		}

		public String getFullname() {
			return fullname.get();
		}

		public void setFullname(String fullname) {
			this.fullname.set(fullname);
		}

		public String getAddress() {
			return address.get();
		}

		public void setAddress(String address) {
			this.address.set(address);
		}

		public Customer getCustomer() {
			Customer customer = new Customer();
			customer.setCustomerId(Integer.valueOf(customer_id.get()));
			customer.setAccount(account.get());
			customer.setAddress(address.get());
			customer.setFullname(fullname.get());
			return customer;
		}
	}
}
