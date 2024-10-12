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
	@FXML private TableColumn<RegistrationModel, CheckBox> select;
	@FXML private TableColumn<RegistrationModel, String> reg_no;	
	@FXML private TableColumn<RegistrationModel, String> ins_name;
	@FXML private TableColumn<RegistrationModel, String> pres_name;
	@FXML private TableColumn<RegistrationModel, String> verified_by;
	@FXML private TableColumn<RegistrationModel, Long> phone;
    private Registration registration;
    private ObservableList<RegistrationModel> registrationData = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
        registration = new Registration();
        bindTableData();
        loadData();
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
                    rs.getString("institution_name"),
                    rs.getString("president_name"),
                    rs.getString("verified_by"),
                    rs.getLong("phone_no")
                ));
            }
            records.setItems(registrationData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeDatabase() {
    	if (registration != null) {
        registration.closeConnection();
    	}
    }
}
