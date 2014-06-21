package com.brokerTool.gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import com.brokerTool.client.ShareClient;
import com.brokerTool.client.StockClient;
import com.brokerTool.gui.BrokerToolFXML.PaneType;
import com.brokerTool.model.Share;
import com.brokerTool.model.Stock;
import com.brokerTool.service.Response;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BrokerToolStockController implements Initializable {

	@FXML
	private TableView<StockBean> stockTable;
	@FXML
	private TableColumn<StockBean, String> symbol;
	@FXML
	private TableColumn<StockBean, String> stockname;
	@FXML
	private TableColumn<StockBean, String> sector;
	@FXML
	static Pane stockPane;
	@FXML
	private Button selectButton;
	@FXML
	private TextField quantity_field;
	@FXML
	private TextField symbol_field;
	@FXML
	private TextField stockname_field;
	@FXML
	private TextField sector_field;

	private int lastSelectedIndex;
	private ArrayList<Stock> stocks;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lastSelectedIndex = -1;
		if (Resource.getinstance().getCalledFromBrokerView()) {
			selectButton.setDisable(true);
		}
		stocks = (ArrayList<Stock>) StockClient.getAllStocks();
		if (!stocks.isEmpty()) {
			updateTable(stocks);
		}
		
		quantity_field.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				String value = ((StringProperty) observable).get();

				if (!value.matches("[0-9]+")) {
					selectButton.setDisable(true);
				} else {
					selectButton.setDisable(false);
				}
			}
			});
	}

	private void updateTable(ArrayList<Stock> stocks) {
		stockTable.getItems().clear();
		for (Stock stock : stocks) {
			stockTable.getItems().add(
					new StockBean(stock.getSymbol(), stock.getStockname(),
							stock.getSector()));
		}

		symbol.setCellValueFactory(new PropertyValueFactory<StockBean, String>(
				"symbol"));
		stockname
				.setCellValueFactory(new PropertyValueFactory<StockBean, String>(
						"stockname"));
		sector.setCellValueFactory(new PropertyValueFactory<StockBean, String>(
				"sector"));
	}

	@FXML
	public void handleMouseClick(MouseEvent arg0) {
		int currentIndex = stockTable.getSelectionModel().getSelectedIndex();
		if (lastSelectedIndex == -1 || lastSelectedIndex != currentIndex) {
			lastSelectedIndex = currentIndex;
		}
	}

	@FXML
	public void handleBackButton(ActionEvent event) {
		if (Resource.getinstance().getCalledFromBrokerView()) {
			Resource.getinstance().setCalledFromBrokerView(false);
			BrokerToolFXML.makeTransition(BrokerToolFXML.BROKER_TOOL_VIEW_NAME,
					PaneType.BROKER_PANE);
		}
		if (Resource.getinstance().getCalledFromSharesView()) {
			Resource.getinstance().setCalledFromSharesView(false);
			BrokerToolFXML.makeTransition(
					BrokerToolFXML.BROKER_TOOL_SHARE_VIEW_NAME,
					PaneType.SHARE_PANE);
		}
	}

	@FXML
	public void handleSelectButton(ActionEvent event) {
		Share share = new Share();
		Stock stock = stocks.get(lastSelectedIndex);
		share.setStock(stock);
		share.setCustomer(Resource.getinstance().getCustomer());

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		share.setPurchasedate(new Timestamp(now.getTime()));

		Random r = new Random();
		double randomValue = 10000 * r.nextDouble();
		share.setPurchaseprice(randomValue);
		ArrayList<Share> shares = (ArrayList<Share>) ShareClient
				.getAllShares();
		share.setSharesId(shares.size()+1);

		share.setQuantity(Integer.valueOf(quantity_field.getText()));
		Resource.getinstance().setCalledFromSharesView(false);
		Response resp = ShareClient.addShare(share);
		System.out.println(resp.toString());
		BrokerToolFXML
				.makeTransition(BrokerToolFXML.BROKER_TOOL_SHARE_VIEW_NAME,
						PaneType.SHARE_PANE);
	}
	
	@FXML
	public void handleAddButton(ActionEvent event) {
		Stock newStock = new Stock();
		newStock.setSymbol(symbol_field.getText());
		newStock.setStockname(stockname_field.getText());
		newStock.setSector(sector_field.getText());
		stockTable.getItems().add(
				new StockBean(newStock.getSymbol(), newStock.getStockname(),
						newStock.getSector()));
		Response resp = StockClient.addStock(newStock);
		System.out.println(resp.toString());
		symbol_field.setText("");
		stockname_field.setText("");
		sector_field.setText("");
	}

	public static class StockBean {
		private final SimpleStringProperty symbol;
		private final SimpleStringProperty stockname;
		private final SimpleStringProperty sector;

		private StockBean(String symbol, String stockname, String sector) {
			this.symbol = new SimpleStringProperty(symbol);
			this.stockname = new SimpleStringProperty(stockname);
			this.sector = new SimpleStringProperty(sector);
		}

		public String getSymbol() {
			return symbol.get();
		}

		public String getStockname() {
			return stockname.get();
		}

		public String getSector() {
			return sector.get();
		}
	}

}
