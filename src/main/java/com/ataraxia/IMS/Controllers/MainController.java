package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import com.ataraxia.IMS.Models.Model;
import javafx.stage.Stage;

public class MainController implements Initializable{
	public BorderPane main_view;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Model.getInstance().getViewFactory().getMenuSwitch().addListener((observableValue, oldVal, newVal)->{
			switch(newVal) {
			case"Register" -> main_view.setCenter(Model.getInstance().getViewFactory().getRegisterView());
			case"FAQ" -> main_view.setCenter(Model.getInstance().getViewFactory().getFaqView());
			case"Settings" -> main_view.setCenter(Model.getInstance().getViewFactory().getSettingsView());
			case"Logout" -> handleLogout();
			case"DetailsView"  -> main_view.setCenter(Model.getInstance().getViewFactory().getDetailsView());
			default -> main_view.setCenter(Model.getInstance().getViewFactory().getHomeView());
			}
		});
	}
	
	private void handleLogout() {
		Model.getInstance().getViewFactory().showLoginWindow();
		
		Stage currentStage = (Stage) main_view.getScene().getWindow();
		currentStage.close();
	}
}
