package com.example.demoapp;

import java.util.List;

public class Bill {
    List<Food> foodList;
    private String totalPrice;
    private String time;

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTime() {
        return time;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    private void calcTotalPrice(){
    }
}
