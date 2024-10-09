package com.ataraxia.IMS.Models;

import com.ataraxia.IMS.Views.ViewFactory;
import javafx.scene.Scene;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private String currentUser;
    private String currentTheme;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.currentTheme = "/Css/darkmode.css"; // Default theme
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
        return currentTheme;
    }
    
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
    
    public void setCurrentTheme(String themeName) {
        if (themeName.equals("Light Mode")) {
            currentTheme = "/Css/lightmode.css";
        } else if (themeName.equals("Dark Mode")) {
            currentTheme = "/Css/darkmode.css";
        }
    }

    public void applyTheme(Scene scene) {
        if (scene != null) {
            String themeStylesheet = getClass().getResource(currentTheme).toExternalForm();
            
            scene.getStylesheets().removeIf(stylesheet -> 
                stylesheet.contains("lightmode.css") || stylesheet.contains("darkmode.css"));
            
            scene.getStylesheets().add(themeStylesheet);
        }
    }
}