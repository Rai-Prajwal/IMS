package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

import com.ataraxia.IMS.Models.Model;

public class MenuController implements Initializable{
	public Button home;
	public Button list;
	public Button help;
	public Button settings;
	public Button logout;
	public Button report;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		addListeners();
	}
	
	private void addListeners() {
		home.setOnAction(event -> onHome());
		list.setOnAction(event -> onRegister());
	}
	
	private void onHome() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Home");
	}
	
	private void onRegister() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Register");
	}
}
