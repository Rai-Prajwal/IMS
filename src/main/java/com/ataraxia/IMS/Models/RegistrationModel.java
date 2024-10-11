package com.ataraxia.IMS.Models;

import javafx.scene.control.CheckBox;


public class RegistrationModel {
    private int registrationNo;
    private String institutionName;
    private String presidentName;
    private String verifiedBy;
    private CheckBox selectCheckBox;

    public RegistrationModel(int registrationNo, String institutionName, String presidentName, String verifiedBy) {
        this.registrationNo = registrationNo;
        this.institutionName = institutionName;
        this.presidentName = presidentName;
        this.verifiedBy = verifiedBy;
        this.selectCheckBox = new CheckBox();
    }

    // Getters
    public int getRegistrationNo() { return registrationNo; }
    public String getInstitutionName() { return institutionName; }
    public String getPresidentName() { return presidentName; }
    public String getVerifiedBy() { return verifiedBy; }
    public CheckBox getSelectCheckBox() { return selectCheckBox; }

    // Setters
    public void setRegistrationNo(int registrationNo) { this.registrationNo = registrationNo; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public void setPresidentName(String presidentName) { this.presidentName = presidentName; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
    public void setSelectCheckBox(CheckBox selectCheckBox) { this.selectCheckBox = selectCheckBox; }
}