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
import android.widget.ListView;
import android.widget.TextView;

public class DisplayBill extends AppCompatActivity {

    private Scene billScene;

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
        billScene = Scene.getSceneForLayout(root, R.layout.display_bill, this);

        displayBill();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayBill(){
        Transition explode = new Explode();
        TransitionManager.go(billScene, explode);

        ListView listView = (ListView)findViewById(R.id.bill_list);
        listView.setAdapter(new CustomListItemAdapter(this, DisplayCart.billManager.getBill().itemList));

        TextView totalPriceTxt = (TextView)findViewById(R.id.bill_totalPrice);
        totalPriceTxt.setText(DisplayCart.billManager.getBill().getTotalPrice() + "VND");
    }

    public void backToHome(View view){
        Bill bill = DisplayCart.billManager.getBill();
        bill.itemList.clear();

        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
    }

    public void backToPrevious(View view){
        finish();
    }
}
