package com.example.demoapp.model;

public class Customer {
    private int ID;
    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;
    private String mName;
    private String mGender;
    private String mDateOfBirth;
    private String mPhoneNumber;
    private String mEmail;

    public Customer(int ID, String mUsername, String mPassword, String mConfirmPassword, String mName, String mGender, String mDateOfBirth, String mPhoneNumber, String mEmail) {
        this.ID = ID;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mConfirmPassword = mConfirmPassword;
        this.mName = mName;
        this.mGender = mGender;
        this.mDateOfBirth = mDateOfBirth;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
    }

    public Customer(String mUsername, String mPassword, String mConfirmPassword, String mName, String mGender, String mDateOfBirth, String mPhoneNumber, String mEmail) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mConfirmPassword = mConfirmPassword;
        this.mName = mName;
        this.mGender = mGender;
        this.mDateOfBirth = mDateOfBirth;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
    }

    public Customer(String mUsername, String mPassword, String mConfirmPassword, String mPhoneNumber, String mEmail) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mConfirmPassword = mConfirmPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
    }

    public Customer() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(String mConfirmPassword) {
        this.mConfirmPassword = mConfirmPassword;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
