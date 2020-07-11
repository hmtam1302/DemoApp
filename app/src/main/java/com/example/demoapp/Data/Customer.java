package com.example.demoapp.Data;

import android.util.Log;

public class Customer {
    private int ID;
    private String Username;
    private String PassWord;
    private String Name;
    private String DateOfBirth;
    private int Gender;
    private String Email;
    private String Phone;

    public Customer(int ID, String username, String passWord, String name, String dateOfBirth, int gender, String email, String phone) {
        this.ID = ID;
        this.Username = username;
        this.PassWord = passWord;
        this.Name = name;
        this.DateOfBirth = dateOfBirth;
        this.Gender = gender;
        this.Email = email;
        this.Phone = phone;
    }

    public Customer(String username, String passWord, String email, String phone) {
        this.Username = username;
        this.PassWord = passWord;
        this.Email = email;
        this.Phone = phone;
    }

    public Customer(int id, String passChanging, String nameChanging, String dobChanging, int genderChanging, String emailChanging, String phoneChanging) {
        this.ID = id;
        this.PassWord = passChanging;
        this.Name = nameChanging;
        this.DateOfBirth = dobChanging;
        this.Gender = genderChanging;
        this.Email = emailChanging;
        this.Phone = phoneChanging;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
