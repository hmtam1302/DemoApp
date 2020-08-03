package com.example.demoapp.Data;

public class Order {
    private int OrderID;
    private int CustomerID;
    private int RestaurantID;
    private int ID;
    private String Name;
    private int Quantity;
    private String Description;
    private String Price;
    private String Status;

    public Order(int orderID, int customerID, int restaurantID, int ID, String name, int quantity, String description, String price, String status) {
        OrderID = orderID;
        CustomerID = customerID;
        RestaurantID = restaurantID;
        this.ID = ID;
        Name = name;
        Quantity = quantity;
        Description = description;
        Price = price;
        Status = status;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        RestaurantID = restaurantID;
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
}
