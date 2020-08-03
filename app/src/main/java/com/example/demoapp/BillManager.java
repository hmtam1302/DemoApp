package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class BillManager {
    private Bill bill = new Bill();

    private ArrayList<Bill> billList = new ArrayList<>();
    private ArrayList<Bill> completedBillList = new ArrayList<>();

    public Bill getBill() {
        return bill;
    }

    public Bill getBillByID(String ID){
        for(int i = 0; i < billList.size(); i++){
            Bill item = billList.get(i);
            if(item.getBillID().equals(ID)) return item;
        }
        for(int i = 0; i < completedBillList.size(); i++){
            Bill item = completedBillList.get(i);
            if(item.getBillID().equals(ID)) return item;
        }
        return null;
    }

    public ArrayList<Bill> getBillList(){ return billList; }

    public ArrayList<Bill> getCompletedBillList(){ return completedBillList; }

    public void addNewBill(Bill bill){
        billList.add(bill);
    }

    public void setNewBill(){
        bill = new Bill();
    }

    public void addToCompletedBillList(String ID){
        for(int i = 0; i < billList.size(); i++){
            Bill bill = billList.get(i);
            if (bill.getBillID().equals(ID)){
                billList.remove(i);
                completedBillList.add(bill);
                break;
            }
        }
    }

    public void setBillList(ArrayList<Bill> billList) {
        this.billList = billList;
    }

    public void setCompletedBillList(ArrayList<Bill> completedBillList) {
        this.completedBillList = completedBillList;
    }
}
