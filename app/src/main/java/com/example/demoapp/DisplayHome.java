package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayHome extends AppCompatActivity {

    public static RestaurantManager restaurantManager = new RestaurantManager();

    private static int count = 0;
    private String selectedRestaurant = null;
    private Scene homeScene;
    private Scene foodScene;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        homeScene = Scene.getSceneForLayout(root, R.layout.orderhome, this);
        foodScene = Scene.getSceneForLayout(root, R.layout.orderfood, this);

        displayHome();

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayHome() {
        Transition explode = new Explode();
        TransitionManager.go(homeScene, explode);

        ImageButton infoBtn = (ImageButton) findViewById(R.id.infobtn);
        infoBtn.setBackgroundColor(android.R.color.white);

        if (count == 0) {
            addRestaurant();
            addFood();
            count++;
        }
        ListView listView = (ListView) findViewById(R.id.listRestaurant);
        List<Restaurant> image_details = restaurantManager.getRestaurantList();
        listView.setAdapter(new CustomListRestaurantAdapter(this, image_details));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFood(v);
            }
        });
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


        restaurantManager.addNewFood("KFC", duiga);
        restaurantManager.addNewFood("KFC", canhga);
        restaurantManager.addNewFood("McDonald's", khoaitaychien);
        restaurantManager.addNewFood("Phúc Long", trahoahong);
        restaurantManager.addNewFood("Phúc Long", tranhanlai);
    }

    public void searchRes(View view) {
        String key = ((EditText) findViewById(R.id.resKey)).getText().toString();
        List<Restaurant> temp = new ArrayList<Restaurant>();

        for (int i = 0; i < restaurantManager.getRestaurantList().size(); i++) {
            Restaurant restaurant = restaurantManager.getRestaurantList().get(i);
            String name = restaurant.getName().toLowerCase();
            if (key != null) {
                if (name.contains(key.toLowerCase())) temp.add(restaurant);
                final ListView listView = (ListView) findViewById(R.id.listRestaurant);
                listView.setAdapter(new CustomListRestaurantAdapter(this, temp));
            } else {
                final ListView listView = (ListView) findViewById(R.id.listRestaurant);
                listView.setAdapter(new CustomListRestaurantAdapter(this, restaurantManager.getRestaurantList()));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayFood(View view) {
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(foodScene, slide);

        final ListView listView = (ListView) findViewById(R.id.listFood);

        TextView resName = (TextView) view.findViewById(R.id.restaurantName);
        String name = resName.getText().toString();
        selectedRestaurant = name;

        //Set title name (Restaurant name in orderfood scene)
        TextView title = (TextView) findViewById(R.id.resTitle);
        title.setText(name);

        for (int i = 0; i < restaurantManager.getRestaurantList().size(); i++) {
            Restaurant restaurant = restaurantManager.getRestaurantList().get(i);
            if (restaurant.getName().equals(name)) {
                listView.setAdapter(new CustomListFoodAdapter(this, restaurantManager.getRestaurantList().get(i).getListFoodData()));
                // When the user clicks on the ListItem
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        displayFoodDetail(v);
                    }
                });
                break;
            }
        }

    }

    public void searchFood(View view) {
        String key = ((EditText) findViewById(R.id.foodKey)).getText().toString();
        List<Food> temp = new ArrayList<>();

        //Find restaurant index
        int index = 0;
        for (int i = 0; i < restaurantManager.getRestaurantList().size(); i++) {
            if (restaurantManager.getRestaurantList().get(i).getName().equals(selectedRestaurant)) {
                index = i;
                break;
            }
        }

        for (int i = 0; i < restaurantManager.getRestaurantList().get(index).getListFoodData().size(); i++) {
            Food food = restaurantManager.getRestaurantList().get(index).getListFoodData().get(i);

            String name = food.getName().toLowerCase();
            if (key != null) {
                if (name.contains(key.toLowerCase())) temp.add(food);
                final ListView listView = (ListView) findViewById(R.id.listFood);
                listView.setAdapter(new CustomListFoodAdapter(this, temp));
            } else {
                final ListView listView = (ListView) findViewById(R.id.listFood);
                List<Food> listFood = restaurantManager.getRestaurantList().get(index).getListFoodData();
                listView.setAdapter(new CustomListFoodAdapter(this, listFood));
            }
        }
    }


    public void logoutActivity(View view) {
        Intent intent = new Intent(this, PopLogout.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayHome(View view) {
        displayHome();
    }

    public void displayInfo(View view) {
        view.setBackgroundColor(Color.parseColor("#FEF8A6"));
        Intent intent = new Intent(this, DisplayInfomation.class);
        startActivity(intent);
    }

    public void displayCart(View view) {
        view.setBackgroundColor(Color.parseColor("#FEF8A6"));
        Intent intent = new Intent(this, DisplayCart.class);
        startActivity(intent);
    }

    public void displayFoodDetail(View view) {
        String foodName = ((TextView) view.findViewById(R.id.foodName)).getText().toString();
        Intent intent = new Intent(this, DisplayFoodDetail.class);
        intent.putExtra("selectedRestaurant", selectedRestaurant);
        intent.putExtra("selectedFood", foodName);
        startActivity(intent);
    }

}
