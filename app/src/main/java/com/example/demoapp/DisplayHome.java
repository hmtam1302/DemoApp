package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demoapp.Data.Food;
import com.example.demoapp.Data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DisplayHome extends AppCompatActivity {
    public static int num_of_order = 0;
    public static int customerID = 0;
    public static int restaurantID = 0;
    private String selectedRestaurant = null;
    private Scene homeScene;
    private Scene foodScene;
    public static ArrayList<Restaurant> resList = new ArrayList<>();
    public static ArrayList<Food> foodList = new ArrayList<>();
    public static ArrayList<BillItem> orderList = new ArrayList<>();

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
        Transition slide = new Slide(Gravity.LEFT);
        TransitionManager.go(homeScene, slide);

        ImageButton infoBtn = (ImageButton) findViewById(R.id.infobtn);
        infoBtn.setBackgroundColor(android.R.color.white);

        ListView listView = (ListView) findViewById(R.id.listRestaurant);
        if(DisplayLogin.resList.size() != 0) {
            customerID = DisplayLogin.customerLogin.getID();
            resList = DisplayLogin.resList;
            foodList = DisplayLogin.foodList;
            orderList = DisplayLogin.orderList;
        }
        else {
            customerID = DisplaySignUp.customerSignUp.getID();
            resList = DisplaySignUp.resList;
            foodList = DisplaySignUp.foodList;
            orderList = DisplaySignUp.orderList;
        }

        // Calculate number of order in system
        for(int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i).getID() > num_of_order) num_of_order = orderList.get(i).getID();
        }
        Log.d("num_of_order", num_of_order+"");
        listView.setAdapter(new CustomListRestaurantAdapter(this, resList));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFood(v);
            }
        });
    }

    /*private void addRestaurant() {
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

    public void searchRes(View view) {
        String key = ((EditText) findViewById(R.id.resKey)).getText().toString();
        List<Restaurant> temp = new ArrayList<Restaurant>();

        for (int i = 0; i < resList.size(); i++) {
            Restaurant restaurant = resList.get(i);
            String name = restaurant.getName().toLowerCase();
            if (key != null) {
                if (name.contains(key.toLowerCase())) temp.add(restaurant);
                final ListView listView = (ListView) findViewById(R.id.listRestaurant);
                listView.setAdapter(new CustomListRestaurantAdapter(this, temp));
            } else {
                final ListView listView = (ListView) findViewById(R.id.listRestaurant);
                listView.setAdapter(new CustomListRestaurantAdapter(this, resList));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayFood(View view) {
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(foodScene, slide);
        final ListView listView = (ListView) findViewById(R.id.listFood);

        TextView resName = (TextView) view.findViewById(R.id.restaurantName);
        ArrayList<Food> foodResList = new ArrayList<>(); // Menu of chosen restaurant

        String name = resName.getText().toString();
        selectedRestaurant = name;
        //int resID = 0;
        Log.d("restaurant chosen: ", selectedRestaurant);
        //Set title name (Restaurant name in orderfood scene)
        TextView title = (TextView) findViewById(R.id.resTitle);
        title.setText(name);

        for (int i = 0; i < resList.size(); i++) {
            Restaurant restaurant = resList.get(i);
            Log.d("Restaurant name ", restaurant.getName());
            Log.d("Restaurant ID ", restaurant.getID()+"");
            if(restaurant.getName().equals(selectedRestaurant)) {
                //resID = resList.get(i).getID();
                restaurantID = resList.get(i).getID();
                break;
            }
        }
        Log.d("ID res", restaurantID+"");
        for (int i = 0; i < foodList.size(); i++) {
            if(foodList.get(i).getRes_ID() == restaurantID) {
                foodResList.add(foodList.get(i));
            }
        }
        Log.d("msg: ", foodResList.size()+"");
        listView.setAdapter(new CustomListFoodAdapter(this, foodResList));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFoodDetail(v);
            }
        });
    }

    public void searchFood(View view) {
        String key = ((EditText) findViewById(R.id.foodKey)).getText().toString();
        List<Food> temp = new ArrayList<>();

        //Find restaurant ID
        int Res_ID = 0;
        for (int i = 0; i < resList.size(); i++) {
            if (resList.get(i).getName().equals(selectedRestaurant)) {
                Res_ID = i;
                break;
            }
        }

        for (int i = 0; i < foodList.size(); i++) {
            Food food = foodList.get(i);

            String name = food.getName().toLowerCase();
            if (key != null) {
                if (name.contains(key.toLowerCase())) temp.add(food);
                final ListView listView = (ListView) findViewById(R.id.listFood);
                listView.setAdapter(new CustomListFoodAdapter(this, temp));
            } else {
                final ListView listView = (ListView) findViewById(R.id.listFood);
                List<Food> listFood = foodList;
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
        Intent intent = new Intent(this, DisplayInfomation.class);
        startActivity(intent);
    }

    public void displayCart(View view) {
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
