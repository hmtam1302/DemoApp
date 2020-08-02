package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class DisplayVendor extends AppCompatActivity {
    private Scene statisticScene;


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
        setContentView(R.layout.vendor_main);

        ViewGroup root = findViewById(R.id.vendorContainer);
        statisticScene = Scene.getSceneForLayout(root, R.layout.bill_cook, this);

        displayStat();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayStat(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(statisticScene, slide);

        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic_pressed));
        ImageButton orderBtn = (ImageButton) findViewById(R.id.order_btn);
        orderBtn.setImageDrawable(getResources().getDrawable(R.drawable.preparing));
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        ListView listView = (ListView)findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getBillList()));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //displayReport(v);
            }
        });
    }
}