package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class BillManager {
    private Bill bill = new Bill();

    private List<Bill> billList = new ArrayList<>();

    public Bill getBill() {
        return bill;
    }

    public Bill getBillByID(String ID){
        for(int i = 0; i < billList.size(); i++){
            Bill item = billList.get(i);
            if(item.getBillID() == ID) return item;
        }
        return null;
    }

    public List<Bill> getBillList(){ return billList; }
    public void addNewBill(Bill bill){
        billList.add(bill);
    }

    public void setNewBill(){
        bill = new Bill();
    }

}
