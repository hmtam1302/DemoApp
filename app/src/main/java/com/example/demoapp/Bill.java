package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    List<Item> itemList;
    private String totalPrice;
    private String time;

    public Bill(){
        itemList = new ArrayList<Item>();
        totalPrice = "0";
        time = "";
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
        calcTotalPrice();
        return totalPrice;
    }

    private void calcTotalPrice(){
        int sum = 0;
        for(int i = 0; i < itemList.size(); i++){
            int itemPrice = Integer.valueOf(itemList.get(i).getPrice()) * Integer.valueOf(itemList.get(i).getQuantity());
            sum += itemPrice;
        }
        totalPrice = String.valueOf(sum);
    }

    public void addNewItem(Item item){
        itemList.add(item);
    }
}
