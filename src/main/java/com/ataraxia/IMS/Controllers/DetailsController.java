package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import com.ataraxia.IMS.Models.RegistrationModel;
import com.ataraxia.IMS.Database.Registration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable{

	@FXML public Button download;
	@FXML public Button edit;
	@FXML public Button delete;
	@FXML public TextField institute_name;
	@FXML public TextField reg_no;
	@FXML public TextField president_name;
	@FXML public TextField phone;
	@FXML public TextField address;
	@FXML public TextField members_count;
	@FXML public TextField reg_date;
	@FXML public TextField exp_date;
	@FXML public TextField verified_by;
	@FXML public TextArea notes;
	private RegistrationModel registrationData;
	
	@Override
	public void initialize(URL Url, ResourceBundle resourceBundle) {
		
	}
	
	public void setRegistrationData(RegistrationModel registrationData) {
		this.registrationData = registrationData;
		
		institute_name.setText(registrationData.getInstitutionName());
		reg_no.setText(registrationData.getRegistrationNo());
		president_name.setText(registrationData.getPresidentName());
		phone.setText(Long.toString(registrationData.getPhoneNo()));
		address.setText(registrationData.getAddress());
		members_count.setText(Integer.toString(registrationData.getMembersCount()));
		reg_date.setText(registrationData.getFormattedRegistrationDate());
		exp_date.setText(registrationData.getFormattedExpiryDate());
		verified_by.setText(registrationData.getVerifiedBy());
		
	}
}
