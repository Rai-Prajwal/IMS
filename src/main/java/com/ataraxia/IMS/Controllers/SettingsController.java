package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Scene;

public class SettingsController implements Initializable{
	public Tab general;
	public Tab account;
	public Tab appearance;
	public Tab notifications;
	public Tab support;
	public RadioButton lightmode;
	public RadioButton darkmode;
	private ToggleGroup theme;
	private String currentTheme;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		
		theme = new ToggleGroup();
		
		lightmode.setToggleGroup(theme);
		darkmode.setToggleGroup(theme);
		
		darkmode.setSelected(true);
		
        theme.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (theme.getSelectedToggle() != null) {
                RadioButton selected = (RadioButton) theme.getSelectedToggle();
                System.out.println("Selected Theme: " + selected.getText());
                
                applyTheme(selected.getText());
            }
        });
    }
	
	private void applyTheme(String themeName) {
		Scene scene = darkmode.getScene();

        if (scene == null) {
            System.out.println("Scene is not initialized yet.");
            return;
        }

        if (currentTheme != null) {
            scene.getStylesheets().remove(currentTheme);
        }

        if (themeName.equals("Light Mode")) {
            currentTheme = getClass().getResource("src/main/resources/Css/lightmode.css").toExternalForm();
        } else if (themeName.equals("Dark Mode")) {
            currentTheme = getClass().getResource("src/main/resources/Css/darkmode.css").toExternalForm();
        }

        if (currentTheme != null) {
            scene.getStylesheets().add(currentTheme);
            System.out.println("Stylesheets applied: " + scene.getStylesheets());
        } else {
            System.out.println("Error: Theme stylesheet path is invalid.");
        }
	}

}
