package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.ataraxia.IMS.Models.Model;
import com.ataraxia.IMS.Models.RegistrationModel;
import com.ataraxia.IMS.Database.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.CheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import com.ataraxia.IMS.Controllers.DetailsController;

public class RegisterController implements Initializable {
	@FXML public TextField search;
	@FXML public ChoiceBox<String> sort;
	@FXML public Label download;
	@FXML public Label file_import;
	@FXML public Label file_export;
	@FXML public TableView<RegistrationModel> records;
	@FXML private TableColumn<RegistrationModel, CheckBox> select;
	@FXML private TableColumn<RegistrationModel, String> reg_no;	
	@FXML private TableColumn<RegistrationModel, String> ins_name;
	@FXML private TableColumn<RegistrationModel, String> pres_name;
	@FXML private TableColumn<RegistrationModel, String> verified_by;
	@FXML private TableColumn<RegistrationModel, Long> phone;
	@FXML private TableColumn<RegistrationModel, String> view;
	@FXML private TableColumn<RegistrationModel, String> renew;
    private Registration registration;
    private ObservableList<RegistrationModel> registrationData = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
        registration = new Registration();
        bindTableData();
        loadData();
        setupViewColumn();
        setupRenewColumn();
        
	}
	
	private void bindTableData () {
	    select.setCellValueFactory(new PropertyValueFactory<>("selectCheckBox"));
	    reg_no.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
	    ins_name.setCellValueFactory(new PropertyValueFactory<>("institutionName"));
	    pres_name.setCellValueFactory(new PropertyValueFactory<>("presidentName"));
	    verified_by.setCellValueFactory(new PropertyValueFactory<>("verifiedBy"));
	    phone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
	}
    

    private void loadData() {
        try {
            ResultSet rs = registration.getAllRegistrations();
            while (rs.next()) {
            	  registrationData.add(new RegistrationModel(
                          rs.getString("registration_no"),
                          rs.getDate("registration_date"),
                          rs.getString("institution_name"),
                          rs.getString("president_name"),
                          rs.getString("address"),
                          rs.getInt("members_count"),
                          rs.getDate("expiry_date"),
                          rs.getString("verified_by"),
                          rs.getLong("phone_no")
                      ));
            }
            records.setItems(registrationData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void setupViewColumn() {
        view.setCellFactory(col -> new TableCell<RegistrationModel, String>() {
            private final Button button = new Button();
            {
                FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                iconView.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px;");
                iconView.getStyleClass().add("table-icon");
                button.setGraphic(iconView);
                button.setStyle("-fx-background-color: transparent;");
                button.setOnAction(event -> handleView(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    private void setupRenewColumn() {
        renew.setCellFactory(col -> new TableCell<RegistrationModel, String>() {
            private final Button button = new Button();
            {
                FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.REFRESH);
                iconView.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px;");
                iconView.getStyleClass().add("table-icon");
                button.setGraphic(iconView);
                button.setStyle("-fx-background-color: transparent;");
                button.setOnAction(event -> handleRenew(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    private void handleView(RegistrationModel registrationModel) {
        if (registrationModel != null) {
            AnchorPane detailsView = Model.getInstance().getViewFactory().getDetailsView();
            
            if (detailsView != null && detailsView.getUserData() instanceof DetailsController) {
                DetailsController detailsController = (DetailsController) detailsView.getUserData();
                detailsController.setRegistrationData(registrationModel);

                Model.getInstance().getViewFactory().getMenuSwitch().set("DetailsView");
            } else {
                System.err.println("DetailsView not found or controller not set.");
            }
        }
    }

    private DetailsController findDetailsController(AnchorPane detailsView) {
        // Option 1: If the controller is set as UserData
        if (detailsView.getUserData() instanceof DetailsController) {
            return (DetailsController) detailsView.getUserData();
        }
        
        // Option 2: If the controller is not set as UserData, try to find it in the children
        for (javafx.scene.Node node : detailsView.getChildren()) {
            if (node.getUserData() instanceof DetailsController) {
                return (DetailsController) node.getUserData();
            }
        }
        
        // If we can't find the controller, log an error or throw an exception
        System.err.println("DetailsController not found!");
        return null;
    }

    private void handleRenew(RegistrationModel registrationModel) {
    }
    
    public void closeDatabase() {
    	if (registration != null) {
        registration.closeConnection();
    	}
    }
}
