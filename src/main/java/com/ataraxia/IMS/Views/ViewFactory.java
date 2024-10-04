package com.ataraxia.IMS.Views;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene; 
import com.ataraxia.IMS.Controllers.MainController;

public class ViewFactory {
	private final StringProperty menuSwitch;
	private AnchorPane homeView;
	private AnchorPane registerView;
	private AnchorPane faqView;
	private AnchorPane settingsView;
	private AnchorPane reportView;
	
	public ViewFactory() {
		this.menuSwitch = new SimpleStringProperty("");
	}
	
	public StringProperty getMenuSwitch() {
		return menuSwitch;
	}
	
	public AnchorPane getHomeView() {
		if(homeView == null) {
			try{
				homeView = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml")).load();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return homeView;
	}
	
	public AnchorPane getRegisterView() {
		if(registerView == null) {
			try {
				registerView = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml")).load();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return registerView;
	}
	
	public AnchorPane getFaqView() {
		if(faqView == null) {
			try {
				faqView = new FXMLLoader(getClass().getResource("/Fxml/FAQ.fxml")).load();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return faqView;
	}
	
	public AnchorPane getSettingsView() {
		if(settingsView == null) {
			try {
				settingsView = new FXMLLoader(getClass().getResource("/Fxml/Settings.fxml")).load();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return settingsView;
	}
	
	public AnchorPane getReportView() {
		if(reportView == null) {
			try {
				reportView = new FXMLLoader(getClass().getResource("/Fxml/Report.fxml")).load();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return reportView;
	}
	
	public void showLoginWindow(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
		createStage(loader);
	}
	
	public void showMainWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
		MainController mainController = new MainController();
		loader.setController(mainController);
		createStage(loader);
	}
	
	private void createStage(FXMLLoader loader) {
		Scene scene = null;
		try {
			scene = new Scene(loader.load());
		}catch(Exception e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Institution Management System");
		stage.show();				
	}
	
	public void closeStage(Stage stage) {
		stage.close();
	}
}

