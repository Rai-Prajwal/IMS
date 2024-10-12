package com.ataraxia.IMS.Models;

import javafx.scene.control.CheckBox;


public class RegistrationModel {
    private String registrationNo;
    private String institutionName;
    private String presidentName;
    private String verifiedBy;
    private Long phoneNo;
    private CheckBox selectCheckBox;

    public RegistrationModel(String registrationNo, String institutionName, String presidentName, String verifiedBy, Long phoneNo) {
        this.registrationNo = registrationNo;
        this.institutionName = institutionName;
        this.presidentName = presidentName;
        this.verifiedBy = verifiedBy;
        this.phoneNo = phoneNo;
        this.selectCheckBox = new CheckBox();
    }

    // Getters
    public String getRegistrationNo() { return registrationNo; }
    public String getInstitutionName() { return institutionName; }
    public String getPresidentName() { return presidentName; }
    public String getVerifiedBy() { return verifiedBy; }
    public Long getPhoneNo() {return phoneNo;}
    public CheckBox getSelectCheckBox() { return selectCheckBox; }

    // Setters
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public void setPresidentName(String presidentName) { this.presidentName = presidentName; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public void setphoneNo(Long phoneNo) {this.phoneNo = phoneNo;}
    public void setSelectCheckBox(CheckBox selectCheckBox) { this.selectCheckBox = selectCheckBox; }
}