package com.ataraxia.IMS.Models;

import com.ataraxia.IMS.Views.ViewFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final StringProperty currentUser = new SimpleStringProperty();
    private final StringProperty currentTheme = new SimpleStringProperty("/Css/darkmode.css");

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public String getCurrentTheme() {
        return currentTheme.get();
    }

    public StringProperty currentThemeProperty() {
        return currentTheme;
    }

    public String getCurrentUser() {
        return currentUser.get();
    }

    public StringProperty currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(String username) {
        this.currentUser.set(username);
    }

    public void setCurrentTheme(String themeName) {
        if (themeName.equals("Light Mode")) {
            currentTheme.set("/Css/lightmode.css");
        } else if (themeName.equals("Dark Mode")) {
            currentTheme.set("/Css/darkmode.css");
        }
    }

    public void applyTheme(Scene scene) {
        if (scene != null) {
            String themeStylesheet = getClass().getResource(currentTheme.get()).toExternalForm();
            
            scene.getStylesheets().removeIf(stylesheet -> 
                stylesheet.contains("lightmode.css") || stylesheet.contains("darkmode.css"));
            
            scene.getStylesheets().add(themeStylesheet);
        }
    }
}