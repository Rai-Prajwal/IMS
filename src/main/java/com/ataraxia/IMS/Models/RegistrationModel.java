package com.ataraxia.IMS.Models;

import javafx.scene.control.CheckBox;


public class RegistrationModel {
    private String registrationNo;
    private int registrationDate;
    private String institutionName;
    private String presidentName;
    private String address;
    private int membersCount;
    private int expiryDate;
    private String verifiedBy;
    private Long phoneNo;
    private CheckBox selectCheckBox;

    public RegistrationModel(String registrationNo, int registrationDate, String institutionName, String presidentName, String address, int membersCount, int expiryDate, String verifiedBy, Long phoneNo) {
        this.registrationNo = registrationNo;
        this.registrationDate = registrationDate;
        this.institutionName = institutionName;
        this.presidentName = presidentName;
        this.address = address;
        this.membersCount = membersCount;
        this.expiryDate = expiryDate;
        this.verifiedBy = verifiedBy;
        this.phoneNo = phoneNo;
        this.selectCheckBox = new CheckBox();
    }

    // Getters
    public String getRegistrationNo() { return registrationNo; }
    public int getRegistrationDate() { return registrationDate;}
    public String getInstitutionName() { return institutionName; }
    public String getPresidentName() { return presidentName; }
    public String getAddress() { return address;}
    public int getMembersCount() {return membersCount;}
    public int getExpiryDate() {return expiryDate;}
    public String getVerifiedBy() { return verifiedBy; }
    public Long getPhoneNo() {return phoneNo;}
    public CheckBox getSelectCheckBox() { return selectCheckBox; }

    // Setters
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }
    public void setRegistrationDate(int registrationDate) {this.registrationDate = registrationDate; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public void setPresidentName(String presidentName) { this.presidentName = presidentName; }
    public void setAddress(String address) { this.address = address; }
    public void membersCount(int membersCount) { this.membersCount = membersCount; }
    public void expiryDate(int expiryDate) { this.expiryDate = expiryDate; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public void setphoneNo(Long phoneNo) {this.phoneNo = phoneNo;}
    public void setSelectCheckBox(CheckBox selectCheckBox) { this.selectCheckBox = selectCheckBox; }
}