package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import com.ataraxia.IMS.Models.Model;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	public TextField username;
	public PasswordField password;
	public CheckBox remember_me;
	public Label forgot_password;
	public Button login;
	public Label error;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		login.setOnAction(event -> onLogin());
	}
	
	private void onLogin() {
		Stage stage = (Stage) error.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showMainWindow();
	}
}
