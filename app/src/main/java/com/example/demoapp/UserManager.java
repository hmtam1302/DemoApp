package com.example.demoapp;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<UserData> userDataList = new ArrayList<>();

    public void addData(String userName, String password, String Email, String phoneNumber ){
        UserData userData = new UserData(userName, password, phoneNumber, Email);
        userDataList.add(userData);
    }
    public UserData getData(String userName){
        for(int i =0; i< userDataList.size(); i++){
            UserData userData = userDataList.get(i);
            if(userData.getUserName().equals(userName)){
                return userData;
            }
        }
        return null;
    }
}


