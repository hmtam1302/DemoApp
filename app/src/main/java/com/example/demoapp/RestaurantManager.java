package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }

    public List<Restaurant> getRestaurantList(){
        return restaurantList;
    }

    public void addNewFood(String resName, Food food){
        for(int i = 0; i< restaurantList.size(); i++){
            Restaurant restaurant = restaurantList.get(i);
            if (restaurant.getName().equals(resName)){
                restaurant.addFood(food);
                break;
            }
        }
    }

    public void setNewListFood(String resName, List<Food> foodList){
        for(int i = 0; i< restaurantList.size(); i++){
            Restaurant restaurant = restaurantList.get(i);
            if (restaurant.getName().equals(resName)){
                restaurant.setListFoodData(foodList);
                break;
            }
        }
    }

}
