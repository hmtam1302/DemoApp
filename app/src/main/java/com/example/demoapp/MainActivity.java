package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
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

public class MainActivity extends AppCompatActivity {

    private Scene mainScene =null;
    private Scene loginScene = null;
    private Scene signupScene = null;
    private Scene informationScene = null;
    private Spinner genderSpinner = null;
    private Scene changepasswordScene = null;
    private Scene homeScene = null;
    private Scene passwordScene = null;
    private Scene foodScene = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide title bar and enable full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);

        ViewGroup root = findViewById(R.id.mainContainer);

        mainScene = Scene.getSceneForLayout(root, R.layout.main, this);
        loginScene = Scene.getSceneForLayout(root, R.layout.login, this);
        signupScene = Scene.getSceneForLayout(root, R.layout.signup, this);
        informationScene =Scene.getSceneForLayout(root, R.layout.accountinfo, this);
        changepasswordScene = Scene.getSceneForLayout(root, R.layout.changepassword, this);
        homeScene = Scene.getSceneForLayout(root, R.layout.orderhome, this);
        passwordScene = Scene.getSceneForLayout(root, R.layout.changepassword, this);
        foodScene = Scene.getSceneForLayout(root, R.layout.orderfood, this);

        mainScene.enter();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loginActivity(View view) {
        TransitionManager.go(loginScene);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToMain(View view){
        Transition explode = new Explode();
        TransitionManager.go(mainScene, explode);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void signupActivity(View view){
        Transition explode = new Explode();
        TransitionManager.go(signupScene, explode);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayInfo(View view){
        Transition explode = new Explode();
        TransitionManager.go(informationScene, explode);
        view.setBackgroundColor(Color.parseColor("#FEF8A6"));
        //Set up gender spinner
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        genderSpinner.setEnabled(false);
    }
    public void editPersonalInfo(View view){
        EditText name = (EditText)findViewById(R.id.name);
        EditText dateofbirth = (EditText)findViewById(R.id.dateofbirth);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phonenumber = (EditText)findViewById(R.id.phonenumber);

        ImageButton saveBtn = (ImageButton)findViewById(R.id.savebtn);

        //Set name, date of birth, gender, email, phone number enable
        name.setEnabled(true);
        dateofbirth.setEnabled(true);
        genderSpinner.setEnabled(true);
        email.setEnabled(true);
        phonenumber.setEnabled(true);
        //Set up save button
        saveBtn.setClickable(true);
        saveBtn.setImageResource(R.drawable.savebtn);
        //Set up edit button
        ImageButton editBtn = (ImageButton)view;
        editBtn.setClickable(false);
        editBtn.setImageResource(R.drawable.editbtndisable);
    }
    public void savePersonalInfo(View view){
        EditText name = (EditText)findViewById(R.id.name);
        EditText dateofbirth = (EditText)findViewById(R.id.dateofbirth);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phonenumber = (EditText)findViewById(R.id.phonenumber);

        ImageButton editBtn = (ImageButton)findViewById(R.id.editbtn);

        //Set name, date of birth, gender, email, phone number disable
        name.setEnabled(false);
        dateofbirth.setEnabled(false);
        genderSpinner.setEnabled(false);
        email.setEnabled(false);
        phonenumber.setEnabled(false);
        //Set up edit button
        editBtn.setClickable(true);
        editBtn.setImageResource(R.drawable.editbtn);
        //Set up save button
        ImageButton saveBtn = (ImageButton)view;
        saveBtn.setClickable(false);
        saveBtn.setImageResource(R.drawable.savebtndisable);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayChangePassword(View view){
        Transition explode = new Explode();
        TransitionManager.go(passwordScene, explode);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayHome(View view){
        Transition explode = new Explode();
        TransitionManager.go(homeScene, explode);

        ImageButton infoBtn = (ImageButton)findViewById(R.id.infobtn);
        infoBtn.setBackgroundColor(android.R.color.white);

        List<Restaurant> image_details = getListRestaurantData();
        final ListView listView = (ListView) findViewById(R.id.listRestaurant);
        listView.setAdapter(new CustomListRestaurantAdapter(this, image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFood(v);
            }
        });
    }
    private  List<Restaurant> getListRestaurantData() {
        List<Restaurant> list = new ArrayList<Restaurant>();
        Restaurant kfc = new Restaurant("KFC", "kfc", "Discription: KFC Chicken","4.5/5.0");
        Restaurant kichikichi = new Restaurant("Kichi Kichi", "kichikichi", "Discription: Hotpot", "4.0/5.0");
        Restaurant loteria = new Restaurant("Lotteria", "lotteria", "Discription: Chiken, Cake, and Chips", "5.0/5.0");
        Restaurant phuclong = new Restaurant("Phúc Long", "phuclong", "Discription: MilkTea", "4.75/5.0");
        Restaurant thecoffeehouse = new Restaurant("THE COFFEE HOUSE", "thecoffeehouse", "Discription: Milktea, Cake", "4.5/5.0");
        Restaurant mcdonald = new Restaurant("McDonald's","mcdonald", "Discription: McDonald's Chicken", "4.5/5.0");


        list.add(kfc);
        list.add(kichikichi);
        list.add(loteria);
        list.add(phuclong);
        list.add(thecoffeehouse);
        list.add(mcdonald);

        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayFood(View view){
        Transition explode = new Explode();
        TransitionManager.go(foodScene, explode);

        List<Food> image_details = getListFoodData();
        final ListView listView = (ListView) findViewById(R.id.listFood);
        listView.setAdapter(new CustomListFoodAdapter(this, image_details));
    }
    private List<Food> getListFoodData() {
        List<Food> list = new ArrayList<Food>();
        Food duiga = new Food("Đùi Gà KFC", "duiga", 15,"Đùi gà chiên thơm giòn", "35000", "4.5/5.0");
        Food canhga = new Food("Cánh Gà KFC", "canhga", 20, "Cánh gà chiên nước mắm", "30000", "4.5/5.0");
        Food khoaitaychien = new Food("Khoai Tây Chiên KFC", "khoaitaychien", 25, "Khoai tây chiên giòn rụm", "20000", "4.5/5.0");
        Food tranhanlai = new Food("Trà Nhãn Lài", "tranhanlai", 40, "Trà nhãn lài ngọt ngào", "50000", "5.0/5.0");
        Food trahoahong = new Food("Trà Hoa Hồng", "trahoahong", 35, "Trà hoa hồng thơm dịu dàng", "45000", "5.0/5.0");


        list.add(duiga);
        list.add(canhga);
        list.add(khoaitaychien);
        list.add(tranhanlai);
        list.add(trahoahong);

        return list;
    }
}
