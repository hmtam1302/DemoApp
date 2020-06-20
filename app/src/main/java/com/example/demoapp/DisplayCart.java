package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DisplayCart extends AppCompatActivity {

    private Scene cartScene;
    private Scene payMethodScene;

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
        cartScene = Scene.getSceneForLayout(root, R.layout.cart, this);
        payMethodScene = Scene.getSceneForLayout(root, R.layout.payment,this);

        displayCart();
}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayCart() {
        Transition explode = new Explode();
        TransitionManager.go(cartScene, explode);

    }

    public void displayHome(View view){
        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayCart(View view){
        displayCart();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayPaymentMethod(View view){
        Transition explode = new Explode();
        TransitionManager.go(payMethodScene, explode);
    }
}
