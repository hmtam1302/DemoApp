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
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoapp.Data.Restaurant;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayReport extends AppCompatActivity {

    private Scene reportScene;

    private int role = 0;

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
        String cmd = getIntent().getStringExtra("cmd");
        if(cmd == null){
            role = 0;
            reportScene = Scene.getSceneForLayout(root, R.layout.report_stall, this);
            displayReportStall();
        } else {
            role = 1;
            reportScene = Scene.getSceneForLayout(root, R.layout.report_system, this);
            displayReportManager();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayReportStall(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(reportScene, slide);

        ImageView logoView = (ImageView) findViewById(R.id.report_stall_logo);
        TextView nameView = (TextView) findViewById(R.id.report_stall_name);
        TextView dateView = (TextView) findViewById(R.id.report_stall_date);
        TextView numOrderView = (TextView) findViewById(R.id.report_stall_num_order);
        TextView ratingView = (TextView) findViewById(R.id.report_stall_rating);
        TextView revenueView = (TextView) findViewById(R.id.report_stall_revenue);

        //Set data for report
        int resID = Integer.valueOf(getIntent().getStringExtra("id"));
        Restaurant restaurant = getRestaurantByID(resID);
        int logoId = getMipmapResIdByName(restaurant.getLogo());
        logoView.setImageResource(logoId); //Set logo image
        nameView.setText(restaurant.getName()); //Set name

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateView.setText(formatter.format(date)); //Set date

        numOrderView.setText(String.valueOf(DisplayCart.billManager.getCompletedBillList().size()));    //Set num of order
        ratingView.setText(restaurant.getRating());
        revenueView.setText(getIntent().getStringExtra("revenue"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayReportManager(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(reportScene, slide);

        TextView numStallView = (TextView) findViewById(R.id.manager_num_stall);
        TextView dateView = (TextView) findViewById(R.id.manager_date);
        TextView numOrderView = (TextView) findViewById(R.id.manager_num_order);
        TextView revenueView = (TextView) findViewById(R.id.manager_total_revenue);

        //Set value
        numStallView.setText(String.valueOf(MainActivity.resList.size()));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateView.setText(formatter.format(date)); //Set date

        //Set numOrder
        int numOrder = 0;
        for(int i = 0; i < MainActivity.orderList.size(); i++){
            if (MainActivity.orderList.get(i).getStatus().equals("completed")) numOrder++;
        }
        numOrderView.setText(String.valueOf(numOrder));

        //Set total revenue
        revenueView.setText(getIntent().getStringExtra("revenue"));
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    public Restaurant getRestaurantByID(int id){
        for(int i = 0; i < DisplayLogin.resList.size(); i++){
            if (DisplayLogin.resList.get(i).getID() == id)
                return DisplayLogin.resList.get(i);
        }
        return null;
    }

    public void printReport(View v){
        Intent intent = new Intent(this, PopSuccessActivity.class);
        if(role == 0){
            intent.putExtra("cmd", "reportStall");
        }
        else {
            intent.putExtra("cmd", "reportManager");
        }
        startActivity(intent);
    }

    public void backToPrevious(View v){
        finish();
    }
}