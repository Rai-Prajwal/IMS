package com.ataraxia.IMS.Views;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene; 
import com.ataraxia.IMS.Controllers.MainController;

public class ViewFactory {
	private AnchorPane homeView;
	
	public ViewFactory() {
		
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

