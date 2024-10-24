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
import javafx.scene.control.Alert;
import java.util.List;
import java.util.Arrays;
import com.ataraxia.IMS.Utils.DateUtils;
import com.github.binodnme.dateconverter.utils.DateBS;

import java.util.Date;

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
	private List<DateTimeFormatter> dateFormatters = Arrays.asList(
			DateTimeFormatter.ofPattern("yyyy/MM/dd"),
	        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
	        DateTimeFormatter.ofPattern("yyyy/M/d"),
	        DateTimeFormatter.ofPattern("yyyy-M-d")
			);
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		updateDateLabel();
		registration = new Registration();
		setupRegistrationButton();
		setupDateFieldPrompts();
	}
	
	private void updateDateLabel() {
		LocalDate today = LocalDate.now();
	
	    DateBS todayBS = DateUtils.convertADToBS(today);
	    
	    String formattedBSDate = todayBS.getYear() + "/" + todayBS.getMonth() + "/" + todayBS.getDay();
	    
		date.setText("Date: "+formattedBSDate);
	}
	
	private void setupRegistrationButton() {
		register.setOnAction(event ->insertRegistration());
	}
	
    private void insertRegistration() {
        try {
            // Parse all input fields
            String registrationNo = registration_no.getText();
            LocalDate registrationDate = parseDate(registration_date.getText().trim());
            LocalDate expiryDate = parseDate(expiry_date.getText().trim());

            // Validate date range before proceeding
            if (!validateDateRange(registrationDate, expiryDate)) {
                return;
            }

            String presidentName = president_name.getText();
            String institutionName = institution_name.getText();
            String addressText = address.getText();
            long phoneNo = Long.parseLong(phone.getText().trim());
            int membersCount = Integer.parseInt(members_count.getText().trim());
            String verifiedBy = verified_by.getText();

            // Proceed with registration if validation passes
            registration.insertRegistration(
                registrationNo, 
                registrationDate, 
                presidentName,
                institutionName, 
                addressText, 
                phoneNo, 
                membersCount, 
                expiryDate, 
                verifiedBy
            );

            showSuccess("Registration Saved", "Registration details have been saved successfully.");
            clearFields();
        } catch (DateTimeParseException e) {
            showAlert("Date Format Error", 
                     "Please enter dates in one of these formats:\n" +
                     "- yyyy/MM/dd (example: 2024/03/22)\n" +
                     "- yyyy-MM-dd (example: 2024-03-22)");
        } catch (NumberFormatException e) {
            showAlert("Number Format Error", 
                     "Please enter valid numbers for:\n" +
                     "- Phone number\n" +
                     "- Members count");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while saving the registration");
            e.printStackTrace();
        }
    }

    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
	
	private void setupDateFieldPrompts() {
        registration_date.setPromptText("yyyy/MM/dd or yyyy-MM-dd");
        expiry_date.setPromptText("yyyy/MM/dd or yyyy-MM-dd");
    }

    private LocalDate parseDate(String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new DateTimeParseException("Date cannot be empty", dateStr, 0);
        }

        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        throw new DateTimeParseException(
            "Date must be in format yyyy/MM/dd or yyyy-MM-dd", 
            dateStr, 
            0
        );
    }

    private boolean validateDateRange(LocalDate registrationDate, LocalDate expiryDate) {
        // Check if registration date is before expiry date
        if (!registrationDate.isBefore(expiryDate)) {
            showAlert("Invalid Date Range", 
                     "Registration date must be before expiry date.\n\n" +
                     "Registration Date: " + registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                     "Expiry Date: " + expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return false;
        }

        // Optional: Check if registration date is not in the future
        LocalDate today = LocalDate.now();
        if (registrationDate.isAfter(today)) {
            showAlert("Invalid Registration Date", 
                     "Registration date cannot be in the future.\n\n" +
                     "Today: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                     "Registration Date: " + registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return false;
        }

        // Optional: Check if expiry date is not in the past
        if (expiryDate.isBefore(today)) {
            showAlert("Invalid Expiry Date", 
                     "Expiry date cannot be in the past.\n\n" +
                     "Today: " + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" +
                     "Expiry Date: " + expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return false;
        }

        return true;
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
