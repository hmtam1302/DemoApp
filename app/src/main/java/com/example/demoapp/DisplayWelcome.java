package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class DisplayWelcome extends AppCompatActivity {
    private Scene mainScene =null;
    //public static RestaurantManager restaurantManager = new RestaurantManager(); //This line for test

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


        // For local variable
        // go();
    }

    public void displayHome(){
        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
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
    public void go(){
        //addRestaurant();
        //addFood();
        Intent intent = new Intent(this, DisplayCook.class);
        startActivity(intent);
    }
}
