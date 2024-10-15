package com.ataraxia.IMS.Views;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene; 
import com.ataraxia.IMS.Controllers.MainController;
import com.ataraxia.IMS.Controllers.SettingsController;
import com.ataraxia.IMS.Models.Model;

public class ViewFactory {
    private final StringProperty menuSwitch;
    private AnchorPane homeView;
    private AnchorPane registerView;
    private AnchorPane faqView;
    private AnchorPane settingsView;
    private AnchorPane reportView;
    private AnchorPane detailsView;
    
    public ViewFactory() {
        this.menuSwitch = new SimpleStringProperty("");
    }
    
    public StringProperty getMenuSwitch() {
        return menuSwitch;
    }
    
    public AnchorPane getHomeView() {
        if(homeView == null) {
            try{
                homeView = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml")).load();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return homeView;
    }
    
    public AnchorPane getRegisterView() {
        if(registerView == null) {
            try {
                registerView = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml")).load();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return registerView;
    }
    
    public AnchorPane getFaqView() {
        if(faqView == null) {
            try {
                faqView = new FXMLLoader(getClass().getResource("/Fxml/FAQ.fxml")).load();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return faqView;
    }
    
    public AnchorPane getSettingsView() {
        if(settingsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Settings.fxml"));
                settingsView = loader.load();
                SettingsController controller = loader.getController();
                controller.initializeTheme();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return settingsView;
    }
    
    public AnchorPane getReportView() {
        if(reportView == null) {
            try {
                reportView = new FXMLLoader(getClass().getResource("/Fxml/Report.fxml")).load();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return reportView;
    }
    public AnchorPane getDetailsView() {
        if(detailsView == null) {
            try {
                detailsView = new FXMLLoader(getClass().getResource("/Fxml/Details.fxml")).load();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return detailsView;
    }
    
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }
    
    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        MainController mainController = new MainController();
        loader.setController(mainController);
        createStage(loader);
    }
    
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            Model.getInstance().applyTheme(scene);
        }catch(Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Institution Management System");
        
        Model.getInstance().applyTheme(scene);
        
        stage.show();                
    }
    
    public void closeStage(Stage stage) {
        stage.close();
    }

    public void applyThemeToAllWindows() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                Scene scene = ((Stage) window).getScene();
                if (scene != null) {
                    Model.getInstance().applyTheme(scene);
                }
            }
        }
    }
}