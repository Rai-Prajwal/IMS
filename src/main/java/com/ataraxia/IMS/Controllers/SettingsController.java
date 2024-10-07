package com.ataraxia.IMS.Controllers;

import com.ataraxia.IMS.Models.Model;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SettingsController implements Initializable {
    public Tab general;
    public Tab account;
    public Tab appearance;
    public Tab notifications;
    public Tab support;
    public RadioButton lightmode;
    public RadioButton darkmode;
    private ToggleGroup theme;
    
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
    }
    
    public void initializeTheme() {
        String currentTheme = Model.getInstance().getCurrentTheme();
        if (currentTheme.contains("lightmode")) {
            lightmode.setSelected(true);
        } else {
            darkmode.setSelected(true);
        }
    }
}