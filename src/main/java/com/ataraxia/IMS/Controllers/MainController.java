package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import com.ataraxia.IMS.Models.Model;

public class MainController implements Initializable{
	public BorderPane main_view;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Model.getInstance().getViewFactory().getMenuSwitch().addListener((observableValue, oldVal, newVal)->{
			switch(newVal) {
			case"Register" -> main_view.setCenter(Model.getInstance().getViewFactory().getRegisterView());
			default -> main_view.setCenter(Model.getInstance().getViewFactory().getHomeView());
			}
		});
	}
}
