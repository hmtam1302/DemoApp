package com.example.demoapp;

public class Item {
    private String name;
    private String price;
    private String quantity;
    private String note;

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getQuantity() {
        return quantity;
    }
}
