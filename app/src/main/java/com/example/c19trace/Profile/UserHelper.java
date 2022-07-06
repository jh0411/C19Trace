package com.example.c19trace.Profile;

public class UserHelper {
    public UserHelper(String name, String email, String phoneNumber, String DOB, String gender, String riskStatus, String vaccinationStatus) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
        this.gender = gender;
        this.riskStatus = riskStatus;
        this.vaccinationStatus = vaccinationStatus;
    }

    String name;
    String email;
    String profileImage;
    String phoneNumber;
    String DOB;
    String gender;
    String riskStatus;
    String vaccinationStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
}
