package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.control.Button;

public class RegisterController implements Initializable {
	@FXML public TextField search;
	@FXML public ChoiceBox<String> sort;
	@FXML public Label download;
	@FXML public Label file_import;
	@FXML public Label file_export;
	@FXML public TableView<RegistrationModel> records;
    private Registration registration;
    private ObservableList<RegistrationModel> registrationData = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
        registration = new Registration();
        setupTableView();
        loadData();
	}
	
    private void setupTableView() {
        TableColumn<RegistrationModel, CheckBox> selectCol = new TableColumn<>("Select");
        selectCol.setCellValueFactory(new PropertyValueFactory<>("selectCheckBox"));

        TableColumn<RegistrationModel, String> institutionNameCol = new TableColumn<>("Institution Name");
        institutionNameCol.setCellValueFactory(new PropertyValueFactory<>("institutionName"));

        TableColumn<RegistrationModel, String> presidentCol = new TableColumn<>("Institution President");
        presidentCol.setCellValueFactory(new PropertyValueFactory<>("presidentName"));

        TableColumn<RegistrationModel, String> verifiedByCol = new TableColumn<>("Verified by");
        verifiedByCol.setCellValueFactory(new PropertyValueFactory<>("verifiedBy"));

        TableColumn<RegistrationModel, Button> viewCol = new TableColumn<>("View");
        viewCol.setCellFactory(param -> new TableCell<>() {
            final Button viewButton = new Button("View");
            {
                viewButton.setOnAction(event -> {
                    RegistrationModel data = getTableView().getItems().get(getIndex());
                    // Handle view action
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });
        
        TableColumn<RegistrationModel, Button> editCol = new TableColumn<>("Edit");
        editCol.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button("Edit");
            {
                editButton.setOnAction(event -> {
                    RegistrationModel data = getTableView().getItems().get(getIndex());
                    // Handle edit action
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        TableColumn<RegistrationModel, Button> renewCol = new TableColumn<>("Renew");
        renewCol.setCellFactory(param -> new TableCell<>() {
            final Button renewButton = new Button("Renew");
            {
                renewButton.setOnAction(event -> {
                    RegistrationModel data = getTableView().getItems().get(getIndex());
                    // Handle renew action
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(renewButton);
                }
            }
        });

        records.getColumns().addAll(selectCol, institutionNameCol, presidentCol, verifiedByCol, viewCol, editCol, renewCol);
    }

    private void loadData() {
        try {
            ResultSet rs = registration.getAllRegistrations();
            while (rs.next()) {
                registrationData.add(new RegistrationModel(
                    rs.getInt("registration_no"),
                    rs.getString("institution_name"),
                    rs.getString("president_name"),
                    rs.getString("verified_by")
                ));
            }
            records.setItems(registrationData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    public void closeDatabase() {
    if (registration != null) {
        registration.closeConnection();
    }
}
}
