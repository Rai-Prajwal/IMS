package com.ataraxia.IMS.Models;

import javafx.scene.control.CheckBox;
import java.time.LocalDate;
import java.sql.Date;
import com.ataraxia.IMS.Utils.DateUtils;

public class RegistrationModel {
    private String registrationNo;
    private LocalDate registrationDate;
    private String institutionName;
    private String presidentName;
    private String address;
    private int membersCount;
    private LocalDate expiryDate;
    private String verifiedBy;
    private Long phoneNo;
    private CheckBox selectCheckBox;

    public RegistrationModel(String registrationNo, Date registrationDate, String institutionName, String presidentName, String address, int membersCount, Date expiryDate, String verifiedBy, Long phoneNo) {
        this.registrationNo = registrationNo;
        this.registrationDate = DateUtils.sqlDateToLocalDate(registrationDate);
        this.institutionName = institutionName;
        this.presidentName = presidentName;
        this.address = address;
        this.membersCount = membersCount;
        this.expiryDate = DateUtils.sqlDateToLocalDate(expiryDate);
        this.verifiedBy = verifiedBy;
        this.phoneNo = phoneNo;
        this.selectCheckBox = new CheckBox();
    }

    // Getters
    public String getRegistrationNo() { return registrationNo; }
    public LocalDate getRegistrationDate() { return registrationDate;}
    public String getFormattedRegistrationDate() {
    	return DateUtils.formatLocalDate(registrationDate);
    }
    public String getInstitutionName() { return institutionName; }
    public String getPresidentName() { return presidentName; }
    public String getAddress() { return address;}
    public int getMembersCount() {return membersCount;}
    public LocalDate getExpiryDate() {return expiryDate;}
    public String getFormattedExpiryDate(){
    	return DateUtils.formatLocalDate(expiryDate);
    }
    public String getVerifiedBy() { return verifiedBy; }
    public Long getPhoneNo() {return phoneNo;}
    public CheckBox getSelectCheckBox() { return selectCheckBox; }

    // Setters
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }
    public void setRegistrationDate(LocalDate registrationDate) {this.registrationDate = registrationDate; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public void setPresidentName(String presidentName) { this.presidentName = presidentName; }
    public void setAddress(String address) { this.address = address; }
    public void membersCount(int membersCount) { this.membersCount = membersCount; }
    public void expiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public void setphoneNo(Long phoneNo) {this.phoneNo = phoneNo;}
    public void setSelectCheckBox(CheckBox selectCheckBox) { this.selectCheckBox = selectCheckBox; }
}