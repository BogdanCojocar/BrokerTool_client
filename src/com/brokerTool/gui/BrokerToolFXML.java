package com.brokerTool.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class BrokerToolFXML extends Application {

	private static Stage stage;

	public enum PaneType {
		BROKER_PANE, CUSTOMER_PANE, SHARE_PANE, STOCK_PANE
	};

	public static final String BROKER_TOOL_VIEW_NAME = "BrokerToolView.fxml";
	public static final String BROKER_TOOL_CUSTOMER_VIEW_NAME = "BrokerToolCustomerView.fxml";
	public static final String BROKER_TOOL_SHARE_VIEW_NAME = "BrokerToolSharesView.fxml";
	public static final String BROKER_TOOL_STOCK_VIEW_NAME = "BrokerToolStockView.fxml";

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		BrokerToolFXML.setScene(primaryStage, BROKER_TOOL_VIEW_NAME);
	}

	public static void makeTransition(String sceneName, PaneType paneType) {
		BrokerToolFXML.setScene(BrokerToolFXML.stage, sceneName);
		Pane newPane = null;
		switch (paneType) {
		case BROKER_PANE:
			newPane = BrokerToolController.brokerPane;
			break;
		case CUSTOMER_PANE:
			newPane = BrokerToolCustomerController.customerPane;
			break;
		case SHARE_PANE:
			newPane = BrokerToolSharesController.sharesPane;
			break;
		case STOCK_PANE:
			newPane = BrokerToolStockController.stockPane;
			break;
		}
		TransitionEffect.makeTransition(newPane);
	}

	public static void setScene(Stage stage, String sceneName) {
		try {
			Parent root = (Parent) FXMLLoader.load(BrokerToolFXML.class
					.getResource(sceneName));
			Scene scene = stage.getScene();
			if (scene == null) {
				scene = new Scene(root);
				scene.getStylesheets().add(
						BrokerToolFXML.class.getResource("application.css")
								.toExternalForm());
				stage.setResizable(false);
				stage.setScene(scene);
				stage.setTitle("Broker Tool");
				stage.show();
			} else {
				stage.getScene().setRoot(root);
			}
			stage.sizeToScene();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
