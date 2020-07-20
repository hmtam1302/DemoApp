package com.example.demoapp.Data;

public class Restaurant {
    private int ID;
    private String Name;
    private String Logo;
    private String Description;
    private String Rating;

    public Restaurant(int ID, String name, String logo, String description, String rating) {
        this.ID = ID;
        Name = name;
        Logo = logo;
        Description = description;
        Rating = rating;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
