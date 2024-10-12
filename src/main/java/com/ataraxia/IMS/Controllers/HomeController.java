package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import com.ataraxia.IMS.Database.Registration;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HomeController implements Initializable {
	@FXML public Text username;
	@FXML public Label date;
	@FXML public Label list_no;
	@FXML public Label list;
	@FXML public Label expired_no;
	@FXML public Label expired;
	@FXML public Label read;
	@FXML public VBox notification;
	@FXML public TextField registration_no;
	@FXML public TextField institution_name;
	@FXML public TextField members_count;
	@FXML public TextField registration_date;
	@FXML public TextField president_name;
	@FXML public TextField address;
	@FXML public TextField expiry_date;
	@FXML public TextField phone;
	@FXML public TextField verified_by;
	@FXML public Button register;
	private Registration registration;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		registration = new Registration();
		setupRegistrationButton();
	}
	
	private void setupRegistrationButton() {
		register.setOnAction(event ->insertRegistration());
	}
	
	private void insertRegistration() {
		try {
			String registrationNo = registration_no.getText();
			int registrationDate = parseDate(registration_date.getText());
			String presidentName = president_name.getText();
			String institutionName = institution_name.getText();
			String addressText = address.getText();
			long phoneNo = Long.parseLong(phone.getText());
			int membersCount = Integer.parseInt(members_count.getText());
			int expiryDate = parseDate(expiry_date.getText());
			String verifiedBy = verified_by.getText();
			
			registration.insertRegistration(registrationNo, registrationDate, presidentName, institutionName, addressText, phoneNo, membersCount, expiryDate, verifiedBy);
			
			clearFields();	
		}catch (NumberFormatException e) {
			System.out.print("Please enter valid numbers for numeric fields.");
		}catch(DateTimeParseException e) {
			System.out.print("Please enter valid dates in the format yyyy/mm/dd.");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int parseDate(String dateString) throws DateTimeParseException{
		LocalDate date = LocalDate.parse(dateString, dateFormatter);
		return(int) date.toEpochDay();
	}
	
	private void clearFields() {
		registration_no.clear();
		registration_date.clear();
		president_name.clear();
		institution_name.clear();
		address.clear();
		phone.clear();
		members_count.clear();
		expiry_date.clear();
		verified_by.clear();
	}
	
	public void closeDatabase() {
		if(registration != null) {
			registration.closeConnection();
		}
	}
}
