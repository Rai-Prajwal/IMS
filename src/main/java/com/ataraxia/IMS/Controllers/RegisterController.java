package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
	public TextField search;
	public ChoiceBox sort;
	public Label download;
	public Label file_import;
	public Label file_export;
	public TableView records;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}
}
