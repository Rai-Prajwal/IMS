package com.ataraxia.IMS.Controllers;

import com.ataraxia.IMS.Database.Usersdb;
import com.ataraxia.IMS.Models.Model;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.*;


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
    @FXML private PasswordField confirm_pass;
    @FXML private Button save;
    
    private Model model;
    private String currentUsername;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = Model.getInstance();
        theme = new ToggleGroup();
        
        lightmode.setToggleGroup(theme);
        darkmode.setToggleGroup(theme);
        
        theme.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (theme.getSelectedToggle() != null) {
                RadioButton selected = (RadioButton) theme.getSelectedToggle();
                System.out.println("Selected Theme: " + selected.getText());
                
                model.setCurrentTheme(selected.getText());
                model.getViewFactory().applyThemeToAllWindows();
            }
        });
        
        try {
            Usersdb.createUsersTable();
            Usersdb.createDefaultAdminAccount();
            loadUserData();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to initialize database.");
        }
        
        save.setOnAction(event -> saveUserData());
        initializeTheme();
        
        model.currentThemeProperty().addListener((observable, oldValue, newValue) -> {
            updateThemeSelection(newValue);
        });
    }
    
    public void initializeTheme() {
        String currentTheme = model.getCurrentTheme();
        updateThemeSelection(currentTheme);
    }
    
    private void updateThemeSelection(String theme) {
        if (theme.contains("lightmode")) {
            lightmode.setSelected(true);
        } else {
            darkmode.setSelected(true);
        }
    }
    
    private void loadUserData() {
        try {
            currentUsername = "admin";
            Usersdb.User user = Usersdb.getUserByUsername(currentUsername);
            
            if (user != null) {
                acc_user.setText(user.getUsername());
                acc_pass.setText("********");
            } else {
                showAlert("Error", "Default admin account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load user data.");
        }
    }
    
    private void saveUserData() {
        String newUsername = edit_user.getText();
        String newPassword = edit_pass.getText();
        String confirmPassword = confirm_pass.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        try {
            boolean updateSuccessful = Usersdb.updateUser(currentUsername, newUsername, newPassword);
            if (updateSuccessful) {
                currentUsername = newUsername;
                acc_user.setText(newUsername);
                acc_pass.setText("********");
                edit_pass.setText("");
                confirm_pass.clear();
                model.setCurrentUser(newUsername);
                showAlert("Success", "User data updated successfully.");
            } else {
                showAlert("Error", "Failed to update user data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}