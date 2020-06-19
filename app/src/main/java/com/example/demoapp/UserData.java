package com.example.demoapp;

public class UserData {
    private String UserName;
    private String Password;
    private String FullName;
    private String Gender;
    private String DateOfBirth;
    private String PhoneNumber;
    private String Email;

    public UserData(String UserName, String Password, String phoneNumber, String Email){
        this.UserName = UserName;
        this.Password = Password;
        this.PhoneNumber = phoneNumber;
        this.Email = Email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getFullName() {
        return FullName;
    }

    public String getGender() {
        return Gender;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
