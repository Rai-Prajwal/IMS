package com.ataraxia.IMS.Controllers;

import com.ataraxia.IMS.Database.Usersdb;
import com.ataraxia.IMS.Models.Model;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

public class SettingsController implements Initializable {
  
	@FXML public Tab general;
	@FXML public Tab account;
	@FXML public Tab appearance;
	@FXML public Tab notifications;
	@FXML public Tab support;
	@FXML public RadioButton lightmode;
	@FXML public RadioButton darkmode;
	@FXML private ToggleGroup theme;
	@FXML private TextField acc_user;
    @FXML private PasswordField acc_pass;
    @FXML private Label edit_user;
    @FXML private Label edit_pass;
    @FXML private Button save;
    
    private String currentUsername;
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        theme = new ToggleGroup();
        
        lightmode.setToggleGroup(theme);
        darkmode.setToggleGroup(theme);
        
        theme.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (theme.getSelectedToggle() != null) {
                RadioButton selected = (RadioButton) theme.getSelectedToggle();
                System.out.println("Selected Theme: " + selected.getText());
                
                Model.getInstance().setCurrentTheme(selected.getText());
                Model.getInstance().getViewFactory().applyThemeToAllWindows();
            }
        });
        
        loadUserData();
        save.setOnAction(event -> saveUserData());
        
    }
    
    public void initializeTheme() {
        String currentTheme = Model.getInstance().getCurrentTheme();
        if (currentTheme.contains("lightmode")) {
            lightmode.setSelected(true);
        } else {
            darkmode.setSelected(true);
        }
    }
    
    private void loadUserData() {
    	try {
    		currentUsername = getCurrentLoggedInUsername();
    		Usersdb.User user = Usersdb.getUserByUsername(currentUsername);
    		
    		if (user != null) {
    			acc_user.setText(user.getUsername());
    			acc_pass.setText("**********");
    			edit_user.setText(user.getUsername());
    		}else {
    			showAlert("Error", "User not found.");
    		}
    	}catch (SQLException e) {
    		e.printStackTrace();
    		showAlert("Error", "Failed to load user data.");
    	}
    }
    
    private void saveUserData() {
        String newUsername = edit_user.getText();
        String newPassword = edit_pass.getText();

        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            try {
                boolean updateSuccessful = Usersdb.updateUser(currentUsername, newUsername, newPassword);
                if (updateSuccessful) {
                    currentUsername = newUsername;
                    acc_user.setText(newUsername);
                    acc_pass.setText("********");
                    edit_pass.setText("");
                    showAlert("Success", "User data updated successfully.");
                } else {
                    showAlert("Error", "Failed to update user data.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database error occurred.");
            }
        } else {
            showAlert("Error", "Username and password cannot be empty.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private String getCurrentLoggedInUsername() {
    	   return "defaultUser";
    }
}