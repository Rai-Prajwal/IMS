package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
	public Text username;
	public Label date;
	public Label list_no;
	public Label list;
	public Label expired_no;
	public Label expired;
	public Label read;
	public VBox notification;
	public TextField registration_no;
	public TextField institution_name;
	public TextField no_members;
	public TextField registration_date;
	public TextField president_name;
	public TextField address;
	public TextField expiry_date;
	public TextField phone;
	public TextField verified_by;
	public Button register;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
	}
}
