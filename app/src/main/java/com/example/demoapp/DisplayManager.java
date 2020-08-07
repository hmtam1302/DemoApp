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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayManager extends AppCompatActivity {
    private Scene statScene;
    private Scene settingScene;


    private int revenue;

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
        setContentView(R.layout.manager_main);
        ViewGroup root = findViewById(R.id.managerContainer);

        statScene = Scene.getSceneForLayout(root, R.layout.manager_stat, this);
        settingScene = Scene.getSceneForLayout(root, R.layout.setting_manager, this);

        if(getIntent().getStringExtra("cmd") == null)
        {
            displayStat();}
        else
        {
            displayManagerSettings();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayStat(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(statScene, slide);
        revenue = 0;
        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.manager_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic_pressed));
        ImageButton settingButton = (ImageButton) findViewById(R.id.manager_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        ListView listView = (ListView)findViewById(R.id.manager_stat_list);
        listView.setAdapter(new CustomListRestaurantManagerAdapter(this, DisplayLogin.resList));

        //Get total revenue
        for(int i = 0; i < DisplayLogin.orderList.size(); i++){
            BillItem item = DisplayLogin.orderList.get(i);
            if (item.getStatus().equals("completed")) revenue += Float.valueOf(item.getPrice());
        }
        TextView revenueTxt = (TextView) findViewById(R.id.manager_revenue);
        revenueTxt.setText(String.valueOf(revenue) + "VNĐ");
    }

    public void displayManagerReport(View v){
        Intent intent = new Intent(this, DisplayReport.class);
        intent.putExtra("cmd", "mamanger");
        intent.putExtra("revenue", String.valueOf(revenue) + "VNĐ");
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayManagerSettings(View v){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");

        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.manager_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic));
        ImageButton settingButton = (ImageButton) findViewById(R.id.manager_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_restaurant);

        listView.setAdapter(new CustomListRestaurantAdapter(this, DisplayLogin.resList));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayRestaurantSettings(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayManagerSettings(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");

        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.manager_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic));
        ImageButton settingButton = (ImageButton) findViewById(R.id.manager_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_restaurant);

        listView.setAdapter(new CustomListRestaurantAdapter(this, DisplayLogin.resList));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayRestaurantSettings(v);
            }
        });
    }

    public void displayRestaurantSettings(View v){
        //Display restaurant settings
        Intent intent =  new Intent(this, DisplayRestaurantSetting.class);
        intent.putExtra("resName", ((TextView) v.findViewById(R.id.restaurantName)).getText().toString());
        startActivity(intent);
    }

    public void displayInfo(View v){
        Intent intent = new Intent(this, DisplayInfomation.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayManagerStat(View v){
        displayStat();
    }

    public void backToPrevious(View v){
        Intent intent = new Intent(this, PopLogout.class);
        startActivity(intent);
    }
}