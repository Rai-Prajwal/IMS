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
import com.ataraxia.IMS.Database.Usersdb;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {
    public TextField username;
    public PasswordField password;
    public TextField passwordVisible;
    public CheckBox remember_me;
    public CheckBox show_password;
    public Button login;
    public Label error;

    private Preferences preferences;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preferences = Preferences.userNodeForPackage(LoginController.class);

        // Load saved username and password if "Remember Me" was checked
        String savedUsername = preferences.get("username", "");
        String savedPassword = preferences.get("password", "");
        boolean isRemembered = preferences.getBoolean("rememberMe", false);

        if (isRemembered) {
            username.setText(savedUsername);
            password.setText(savedPassword);
            passwordVisible.setText(savedPassword);
            remember_me.setSelected(true);
        }

        // Toggle password visibility based on "Show Password" checkbox
        show_password.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordVisible.setText(password.getText());
                passwordVisible.setVisible(true);
                password.setVisible(false);
            } else {
                password.setText(passwordVisible.getText());
                password.setVisible(true);
                passwordVisible.setVisible(false);
            }
        });

        // Ensure password and passwordVisible stay synchronized
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!show_password.isSelected()) {
                passwordVisible.setText(newValue);
            }
        });

        passwordVisible.textProperty().addListener((observable, oldValue, newValue) -> {
            if (show_password.isSelected()) {
                password.setText(newValue);
            }
        });

        login.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        String enteredUsername = username.getText();
        String enteredPassword = show_password.isSelected() ? passwordVisible.getText() : password.getText();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            boolean isAuthenticated = Usersdb.authenticateUser(enteredUsername, enteredPassword);
            if (isAuthenticated) {
                // Save preferences if "Remember Me" is checked
                if (remember_me.isSelected()) {
                    preferences.put("username", enteredUsername);
                    preferences.put("password", enteredPassword);
                    preferences.putBoolean("rememberMe", true);
                } else {
                    // Clear preferences if "Remember Me" is unchecked
                    preferences.remove("username");
                    preferences.remove("password");
                    preferences.putBoolean("rememberMe", false);
                }

                Model.getInstance().setCurrentUser(enteredUsername);
                Stage stage = (Stage) error.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showMainWindow();
            } else {
                showError("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred during login. Please try again.");
        }
    }

    private void showError(String message) {
        error.setText(message);
        error.setVisible(true);
    }
}
