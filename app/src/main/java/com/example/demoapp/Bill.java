package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    List<BillItem> billItemList;
    private String billID;
    private String totalPrice;
    private String time;
    private boolean isFinished;

    public Bill(){
        billItemList = new ArrayList<BillItem>();
        totalPrice = "0";
        time = "";
    }

    public void setFinished(boolean isFinished){ this.isFinished = isFinished; }

    public boolean getFinished(){ return isFinished; }

    public void setBillID(String billID) { this.billID = billID; }

    public String getBillID() { return billID; }

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
        for(int i = 0; i < billItemList.size(); i++){
            int itemPrice = Integer.valueOf(billItemList.get(i).getPrice()) * Integer.valueOf(billItemList.get(i).getQuantity());
            sum += itemPrice;
        }
        totalPrice = String.valueOf(sum);
    }

    public void addNewItem(BillItem billItem){
        billItemList.add(billItem);
    }
}
