package com.brokerTool.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.brokerTool.client.ShareClient;
import com.brokerTool.gui.BrokerToolFXML.PaneType;
import com.brokerTool.model.Share;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BrokerToolSharesController implements Initializable {

	@FXML
	private TableView<ShareBean> sharesTable;
	@FXML
	private TableColumn<ShareBean, String> shares_id;
	@FXML
	private TableColumn<ShareBean, String> symbol;
	@FXML
	private TableColumn<ShareBean, String> quantity;
	@FXML
	private TableColumn<ShareBean, String> purchaseprice;
	@FXML
	private TableColumn<ShareBean, String> purchasedate;
	@FXML
	private PieChart sharesPieChart;
	@FXML
	static Pane sharesPane;

	private ArrayList<Share> shares;
	private int lastSelectedIndex;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lastSelectedIndex = -1;
		shares = (ArrayList<Share>) ShareClient
				.selectSharesByCustomerId(Resource.getinstance().getShare()
						.getSharesId());
		updateTable(shares);
		updateChart(shares);
	}

	private void updateTable(ArrayList<Share> shares) {
		sharesTable.getItems().clear();
		for (Share share : shares) {
			sharesTable.getItems().add(
					new ShareBean(share.getSharesId().toString(), share
							.getStock().getSymbol(), share.getQuantity()
							.toString(), Double.toString(share
							.getPurchaseprice()), share.getPurchasedate()
							.toString()));
		}

		shares_id
				.setCellValueFactory(new PropertyValueFactory<ShareBean, String>(
						"shares_id"));
		symbol.setCellValueFactory(new PropertyValueFactory<ShareBean, String>(
				"symbol"));
		quantity.setCellValueFactory(new PropertyValueFactory<ShareBean, String>(
				"quantity"));
		purchaseprice
				.setCellValueFactory(new PropertyValueFactory<ShareBean, String>(
						"purchaseprice"));
		purchasedate
				.setCellValueFactory(new PropertyValueFactory<ShareBean, String>(
						"purchasedate"));
	}

	private void updateChart(ArrayList<Share> shares) {

		ObservableList<Data> chartData = FXCollections.observableArrayList();
		for (Share share : shares) {
			chartData.add(new Data(share.getStock().getSymbol(), share
					.getQuantity()));
		}
		sharesPieChart.getData().clear();
		sharesPieChart.getData().addAll(chartData);
	}

	@FXML
	public void handleBuy(ActionEvent event) {
		BrokerToolFXML.makeTransition(BrokerToolFXML.BROKER_TOOL_STOCK_VIEW_NAME, PaneType.STOCK_PANE);
	}

	@FXML
	public void handleSell(ActionEvent event) {
		if (lastSelectedIndex != -1) {
			sharesTable.getItems().remove(lastSelectedIndex);
			ShareClient.deleteShareById(lastSelectedIndex+1);
		}
	}

	@FXML
	public void handleBackButton(ActionEvent event) {
		BrokerToolFXML.makeTransition(
				BrokerToolFXML.BROKER_TOOL_CUSTOMER_VIEW_NAME,
				PaneType.CUSTOMER_PANE);
	}
	
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		int currentIndex = sharesTable.getSelectionModel().getSelectedIndex();
		if (lastSelectedIndex == -1 || lastSelectedIndex != currentIndex) {
			lastSelectedIndex = currentIndex;
		}
	}

	public static class ShareBean {
		private final SimpleStringProperty shares_id;
		private final SimpleStringProperty symbol;
		private final SimpleStringProperty quantity;
		private final SimpleStringProperty purchaseprice;
		private final SimpleStringProperty purchasedate;

		public ShareBean(String shares_id, String symbol, String quantity,
				String purchaseprice, String purchasedate) {
			this.shares_id = new SimpleStringProperty(shares_id);
			this.symbol = new SimpleStringProperty(symbol);
			this.quantity = new SimpleStringProperty(quantity);
			this.purchaseprice = new SimpleStringProperty(purchaseprice);
			this.purchasedate = new SimpleStringProperty(purchasedate);
		}

		public String getShares_id() {
			return shares_id.get();
		}

		public String getSymbol() {
			return symbol.get();
		}

		public String getQuantity() {
			return quantity.get();
		}

		public String getPurchaseprice() {
			return purchaseprice.get();
		}

		public String getPurchasedate() {
			return purchasedate.get();
		}
	}

}
