package com.ataraxia.IMS.Controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import com.ataraxia.IMS.Models.Model;
import com.ataraxia.IMS.Database.Registration;
import com.ataraxia.IMS.Utils.DateUtils;
import com.ataraxia.Models.BSDate;
import com.ataraxia.Utils.Converter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialize user and database connection
            username.setText(Model.getInstance().getCurrentUser());
            registration = new Registration();
            
            // Update UI elements
            updateCounts();
            setupRegistrationButton();
            setupDateFieldPrompts();
            updateDateLabel();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to initialize data");
            e.printStackTrace();
        }
    }

    private void updateCounts() throws SQLException {
        list_no.setText(String.valueOf(registration.getTotalRegistrations()));
        expired_no.setText(String.valueOf(registration.getExpiredRegistrationsCount()));
    }

    private void updateDateLabel() {
        BSDate todayBS = DateUtils.getCurrentBSDate();
        date.setText("Date: " + todayBS.toString());
    }

    private void setupRegistrationButton() {
        register.setOnAction(event -> insertRegistration());
    }

    private void insertRegistration() {
        try {
            // Parse BS dates
            BSDate regDate = DateUtils.parseBSDate(registration_date.getText().trim());
            BSDate expDate = DateUtils.parseBSDate(expiry_date.getText().trim());
            
            // Validate dates
            validateBSDates(regDate, expDate);

            // Parse other fields and validate
            String registrationNo = registration_no.getText();
            String presidentName = president_name.getText();
            String institutionName = institution_name.getText();
            String addressText = address.getText();
            long phoneNo = Long.parseLong(phone.getText().trim());
            int membersCount = Integer.parseInt(members_count.getText().trim());
            String verifiedBy = verified_by.getText();

            // Insert registration with BS dates
            registration.insertBSRegistration(
                registrationNo,
                regDate,
                presidentName,
                institutionName,
                addressText,
                phoneNo,
                membersCount,
                expDate,
                verifiedBy
            );

            showSuccess("Registration Saved", "Registration details have been saved successfully.");
            clearFields();
            updateCounts();

        } catch (DateTimeParseException e) {
            showAlert("Date Format Error", "Please enter dates in BS format: yyyy/mm/dd");
        } catch (NumberFormatException e) {
            showAlert("Number Format Error", 
                     "Please enter valid numbers for:\n" +
                     "- Phone number\n" +
                     "- Members count");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void validateBSDates(BSDate regDate, BSDate expDate) throws Exception {
        // Validate using DateConverter
        Converter.validateBsDate(regDate);
        Converter.validateBsDate(expDate);
        
        BSDate today = DateUtils.getCurrentBSDate();

        if (regDate.isAfter(today)) {
            throw new Exception("Registration date cannot be in the future.\n\n" +
                              "Today: " + today.toString() + "\n" +
                              "Registration Date: " + regDate.toString());
        }

        if (expDate.isBefore(today)) {
            throw new Exception("Expiry date cannot be in the past.\n\n" +
                              "Today: " + today.toString() + "\n" +
                              "Expiry Date: " + expDate.toString());
        }

        if (regDate.isAfter(expDate)) {
            throw new Exception("Registration date must be before expiry date.\n\n" +
                              "Registration Date: " + regDate.toString() + "\n" +
                              "Expiry Date: " + expDate.toString());
        }
    }

    private void setupDateFieldPrompts() {
        registration_date.setPromptText("BS Date (yyyy/mm/dd)");
        expiry_date.setPromptText("BS Date (yyyy/mm/dd)");
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
        if (registration != null) {
            registration.closeConnection();
        }
    }
}