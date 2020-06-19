package com.example.demoapp;

public class Food {
    private String name;
    private String logo;
    private int quantity;
    private String description;
    private String price;
    private String rating;

    public Food(String name, String logo, int quantity, String description, String price, String rating){
        this.name = name;
        this.logo = logo;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

