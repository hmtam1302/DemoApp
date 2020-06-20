package com.example.demoapp;

import java.util.List;

public class BillManager {
    private Bill bill = new Bill();
    private List<Item> itemList;

    public Bill getBill() {
        return bill;
    }
    public List<Item> getItemList() {
        return itemList;
    }
}
