package com.example.demoapp.Data;

public class Food {
    private int ID;
    private int Res_ID;
    private String Name;
    private String Logo;
    private int Quantity;
    private String Description;
    private String Price;
    private String Rating;
    private String Enable;

    public Food(int ID, int res_ID, String name, String logo, int quantity, String description, String price, String rating, String enable) {
        this.ID = ID;
        Res_ID = res_ID;
        Name = name;
        Logo = logo;
        Quantity = quantity;
        Description = description;
        Price = price;
        Rating = rating;
        Enable = enable;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRes_ID() {
        return Res_ID;
    }

    public void setRes_ID(int res_ID) {
        Res_ID = res_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getEnable() {
        return Enable;
    }

    public void setEnable(String enable) {
        Enable = enable;
    }
}
