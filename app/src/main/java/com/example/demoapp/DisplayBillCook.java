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
import android.widget.ListView;
import android.widget.TextView;

public class DisplayBillCook extends AppCompatActivity {
    private Scene billCookScene;

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
        billCookScene = Scene.getSceneForLayout(root, R.layout.bill_cook, this);

        displayBillCook();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayBillCook(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCookScene, slide);

        ListView listView = (ListView)findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getBillList()));
    }

    public void backToPrevious(View v){
        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
    }

}