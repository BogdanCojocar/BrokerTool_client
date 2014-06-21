package com.brokerTool.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.brokerTool.client.BrokerClient;
import com.brokerTool.client.JerseyClient;
import com.brokerTool.gui.BrokerToolFXML.PaneType;
import com.brokerTool.model.Broker;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BrokerToolController implements Initializable {

	@FXML
	private TableView<BrokerBean> brokerTable;
	@FXML
	private TableColumn<BrokerBean, String> broker_id;
	@FXML
	private TableColumn<BrokerBean, String> name;
	@FXML
	private TableColumn<BrokerBean, String> address;
	@FXML
	static Pane brokerPane;

	private ArrayList<Broker> brokers;
	private int lastSelectedIndex;
	private Broker lastSelectedBroker;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JerseyClient.init();
		lastSelectedIndex = -1;
		brokers = (ArrayList<Broker>) BrokerClient.getAllBorkers();
		updateTable(brokers);

	}

	private void updateTable(ArrayList<Broker> brokers) {
		if (brokers != null) {
			brokerTable.getItems().clear();
			for (Broker broker : brokers) {
				brokerTable.getItems().add(
						new BrokerBean(broker.getBrokerId().toString(), broker
								.getBrokername(), broker.getAddress()));
			}

			broker_id
					.setCellValueFactory(new PropertyValueFactory<BrokerBean, String>(
							"broker_id"));
			name.setCellValueFactory(new PropertyValueFactory<BrokerBean, String>(
					"name"));
			address.setCellValueFactory(new PropertyValueFactory<BrokerBean, String>(
					"address"));
		}
	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		int currentIndex = brokerTable.getSelectionModel().getSelectedIndex();
		if (lastSelectedIndex == -1 || lastSelectedIndex != currentIndex) {
			lastSelectedIndex = currentIndex;
			lastSelectedBroker = brokers.get(lastSelectedIndex);
		}
	}

	@FXML
	public void showCustomers(ActionEvent event) {
		Resource.getinstance().setBroker(lastSelectedBroker);
		BrokerToolFXML.makeTransition(BrokerToolFXML.BROKER_TOOL_CUSTOMER_VIEW_NAME, PaneType.CUSTOMER_PANE);
	}
	
	@FXML
	public void showStocks(ActionEvent event) {
		Resource.getinstance().setCalledFromBrokerView(true);
		BrokerToolFXML.makeTransition(BrokerToolFXML.BROKER_TOOL_STOCK_VIEW_NAME, PaneType.STOCK_PANE);
	}

	public static class BrokerBean {
		private final SimpleStringProperty broker_id;
		private final SimpleStringProperty name;
		private final SimpleStringProperty address;

		private BrokerBean(String broker_id, String name, String address) {
			this.broker_id = new SimpleStringProperty(broker_id);
			this.name = new SimpleStringProperty(name);
			this.address = new SimpleStringProperty(address);
		}

		public String getBroker_id() {
			return broker_id.get();
		}

		public String getName() {
			return name.get();
		}

		public String getAddress() {
			return address.get();
		}
	}
}
