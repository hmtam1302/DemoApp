package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DisplayWelcome extends AppCompatActivity {
    private Scene mainScene =null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide title bar and enable full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        //Set main content view
        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.mainContainer);

        mainScene = Scene.getSceneForLayout(root, R.layout.main, this);


        mainScene.enter();
    }
    public void displayLogin(View view){
        Intent intent = new Intent(this, DisplayLogin.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToMain(View view){
        Transition slide = new Slide(Gravity.LEFT);
        TransitionManager.go(mainScene, slide);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displaySignup(View view){
        Intent intent = new Intent(this, DisplaySignUp.class);
        startActivity(intent);
    }

//This code below for test
    /*public void go(){
        addRestaurant();
        addFood();
        Intent intent = new Intent(this, DisplayCook.class);
        startActivity(intent);
    }

    private void addRestaurant() {
        Restaurant kfc = new Restaurant("KFC", "kfc", "Discription: KFC Chicken", "4.5/5.0");
        Restaurant kichikichi = new Restaurant("Kichi Kichi", "kichikichi", "Discription: Hotpot", "4.0/5.0");
        Restaurant lotteria = new Restaurant("Lotteria", "lotteria", "Discription: Chiken, Cake, and Chips", "5.0/5.0");
        Restaurant phuclong = new Restaurant("Phúc Long", "phuclong", "Discription: MilkTea", "4.75/5.0");
        Restaurant thecoffeehouse = new Restaurant("THE COFFEE HOUSE", "thecoffeehouse", "Discription: Milktea, Cake", "4.5/5.0");
        Restaurant mcdonald = new Restaurant("McDonald's", "mcdonald", "Discription: McDonald's Chicken", "4.5/5.0");


        restaurantManager.addRestaurant(kfc);
        restaurantManager.addRestaurant(kichikichi);
        restaurantManager.addRestaurant(lotteria);
        restaurantManager.addRestaurant(phuclong);
        restaurantManager.addRestaurant(thecoffeehouse);
        restaurantManager.addRestaurant(mcdonald);
    }

    private void addFood() {
        Food duiga = new Food("Đùi Gà KFC", "duiga", 15, "Đùi gà chiên thơm giòn", "35000", "4.5/5.0");
        Food canhga = new Food("Cánh Gà KFC", "canhga", 20, "Cánh gà chiên nước mắm", "30000", "4.5/5.0");
        Food khoaitaychien = new Food("Khoai Tây Chiên KFC", "khoaitaychien", 25, "Khoai tây chiên giòn rụm", "20000", "4.5/5.0");
        Food tranhanlai = new Food("Trà Nhãn Lài", "tranhanlai", 40, "Trà nhãn lài ngọt ngào", "50000", "5.0/5.0");
        Food trahoahong = new Food("Trà Hoa Hồng", "trahoahong", 35, "Trà hoa hồng thơm dịu dàng", "45000", "5.0/5.0");
        Food hamburger = new Food("Hamburger", "hamburger",20,"Hamburger Bò", "55000", "4.5/5.0");
        Food pepsi = new Food("Pepsi", "pepsi", 60,"Đã quá Pepsi ơi!", "10000","5.0/5.0");
        Food trasuaphuclong = new Food("Trà Sữa", "trasuaphuclong",50,"Trà sữa Phúc Long","25000","5.0/5.0");
        Food coffeephuclong = new Food("Coffee","coffee",50,"Coffee Phúc Long", "30000","5.0/5.0");
        Food hamburgermcdonal = new Food("Hamburger", "hamburgermcdonal",20,"Hamburger Bò", "55000", "4.5/5.0");
        Food duigamcdonal = new Food("Đùi Gà McDonald's", "duigamcdonal", 15, "Đùi gà chiên thơm giòn", "35000", "4.5/5.0");
        Food sodachanhtch = new Food("Soda chanh", "sodachanhtch",50,"Soda chanh", "50000", "4.7/5.0");
        Food combobuffet = new Food("Combo Buffet","combobuffetkichikichi", 100,"Combo Buffet", "100000", "5.0/5.0");
        Food comga = new Food("Cơm gà", "comga",50,"Cơm gà viên","35000","5.0/5.0");


        restaurantManager.addNewFood("KFC", duiga);
        restaurantManager.addNewFood("KFC", canhga);
        restaurantManager.addNewFood("KFC", hamburger);
        restaurantManager.addNewFood("KFC", pepsi);
        restaurantManager.addNewFood("McDonald's", khoaitaychien);
        restaurantManager.addNewFood("McDonald's", hamburgermcdonal);
        restaurantManager.addNewFood("McDonald's", duigamcdonal);
        restaurantManager.addNewFood("Phúc Long", trahoahong);
        restaurantManager.addNewFood("Phúc Long", tranhanlai);
        restaurantManager.addNewFood("Phúc Long", trasuaphuclong);
        restaurantManager.addNewFood("Phúc Long", coffeephuclong);
        restaurantManager.addNewFood("THE COFFEE HOUSE", sodachanhtch);
        restaurantManager.addNewFood("Kichi Kichi", combobuffet);
        restaurantManager.addNewFood("Lotteria",comga);
    }

     */
}
