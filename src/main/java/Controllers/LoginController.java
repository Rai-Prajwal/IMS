package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	public TextField username;
	public PasswordField password;
	public CheckBox remember_me;
	public Label forgot_password;
	public Button login;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		
	}
}
