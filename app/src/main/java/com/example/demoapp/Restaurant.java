package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String logoName;
    private String discription;
    private String rating;

    private List<Food> listFoodData = new ArrayList<Food>();

    public Restaurant(String name, String logoName, String discription, String rating){
        this.name = name;
        this.logoName = logoName;
        this.discription = discription;
        this.rating = rating;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoName() {
        return logoName;
    }

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }
    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Food> getListFoodData() {
        return listFoodData;
    }

    public void setListFoodData(List<Food> listFoodData) {
        this.listFoodData = listFoodData;
    }

    public void addFood(Food food){
        listFoodData.add(food);
    }
}
