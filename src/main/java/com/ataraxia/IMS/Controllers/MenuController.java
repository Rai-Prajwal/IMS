package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import com.ataraxia.IMS.Models.Model;

public class MenuController implements Initializable{
	public Button home;
	public Button list;
	public Button faq;
	public Button settings;
	public Button logout;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		addListeners();
	}
	
	private void addListeners() {
		home.setOnAction(event -> onHome());
		list.setOnAction(event -> onRegister());
		faq.setOnAction(event -> onFaq());
		settings.setOnAction(event -> onSettings());
		logout.setOnAction(event -> onLogout());
	}
	
	private void onHome() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Home");
	}
	
	private void onRegister() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Register");
	}
	
	private void onFaq() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("FAQ");
	}
	
	private void onSettings() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Settings");
	}
	
	private void onLogout() {
		Model.getInstance().getViewFactory().getMenuSwitch().set("Logout");
	}
}
