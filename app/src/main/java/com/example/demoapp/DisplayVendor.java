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

import com.example.demoapp.Data.Food;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DisplayVendor extends AppCompatActivity {
    private Scene statisticScene;
    private Scene settingScene;

    private static int resID = DisplayLogin.currentUser.getRestaurantID();   //Get current restaurant of the cook
    private ArrayList<Food> listFood;

    private static ArrayList<Bill> prepareBillList = new ArrayList<>();
    private static ArrayList<Bill> completedBillList = new ArrayList<>();
    private static int count = 0;

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

        statisticScene = Scene.getSceneForLayout(root, R.layout.vendor_statistic, this);
        settingScene = Scene.getSceneForLayout(root, R.layout.setting, this);


        getFoodList();
        if(count == 0){
            getBillList();
            DisplayCart.billManager.setBillList(prepareBillList);
            DisplayCart.billManager.setCompletedBillList(completedBillList);
        }
        String cmd = getIntent().getStringExtra("cmd");
        if(cmd != null){
            displayVendorSettings();
        } else{
            displayStat();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayStat(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(statisticScene, slide);

        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.vendor_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic_pressed));
        ImageButton settingButton = (ImageButton) findViewById(R.id.vendor_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        ListView listView = (ListView)findViewById(R.id.vendor_completedBillList);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getCompletedBillList()));

        //Calculate total revenue
        int revenue = 0;
        for(int i = 0; i < DisplayCart.billManager.getCompletedBillList().size(); i++){
            Bill bill = DisplayCart.billManager.getCompletedBillList().get(i);
            revenue += Float.valueOf(bill.getTotalPrice());
        }
        TextView revenueTxt = (TextView) findViewById(R.id.vendor_revenue);
        revenueTxt.setText(String.valueOf(revenue) + "000 VNĐ");
    }

    private void getBillList(){
        ArrayList<BillItem> orderList = DisplayLogin.orderList;
        while(orderList.size() != 0){
            Bill bill = new Bill();
            bill.addNewItem(orderList.get(0));      //Add the first element in the orderList
            String status = orderList.get(0).getStatus();
            int id = orderList.get(0).getID();
            bill.setStatus(status);
            bill.setBillID(Integer.toString(id));
            orderList.remove(0);            //then remove it and set the status of the bill

            //Add bill item with the same ID
            int i = 0;
            while(i < orderList.size()){
                if(orderList.get(i).getID() == id){
                    bill.addNewItem(orderList.get(i));
                    orderList.remove(i);
                    i--;
                }
                i++;
            }

            //Add bill to prepareBillList or completedBillList
            bill.calcTotalPrice();  //Calculate total price of bill
            if(!status.equals("completed")){
                prepareBillList.add(bill);
            }
            else{
                completedBillList.add(bill);
            }
        }
        count++;
    }

    public void getFoodList(){
        //Get the food list of the restaurant
        listFood = new ArrayList<>();
        for(int i = 0; i < MainActivity.foodList.size(); i++){
            Food food = MainActivity.foodList.get(i);
            if(food.getRes_ID() == resID) listFood.add(food);
        }
    }

    public void displayReport(View v){
        String revenueText = ((TextView) findViewById(R.id.vendor_revenue)).getText().toString();
        Intent intent = new Intent(this, DisplayReport.class);
        intent.putExtra("revenue", revenueText);
        intent.putExtra("id", String.valueOf(resID));
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayVendorSettings(View v){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");
        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.vendor_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic));
        ImageButton settingButton = (ImageButton) findViewById(R.id.vendor_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));
        ImageButton infoButton = (ImageButton) findViewById(R.id.vendor_info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_food);

        listView.setAdapter(new CustomListFoodAdapter(this, listFood));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFoodSettings(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayVendorSettings(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");
        //Set navigation bar
        ImageButton statBtn = (ImageButton) findViewById(R.id.vendor_stat_btn);
        statBtn.setImageDrawable(getResources().getDrawable(R.drawable.statistic));
        ImageButton settingButton = (ImageButton) findViewById(R.id.vendor_setting_btn);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));
        ImageButton infoButton = (ImageButton) findViewById(R.id.vendor_info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_food);

        listView.setAdapter(new CustomListFoodAdapter(this, listFood));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayFoodSettings(v);
            }
        });
    }

    public void displayFoodSettings(View v){
        // Implement code for change food settings
        Intent intent = new Intent(this, DisplayFoodSetting.class);
        intent.putExtra("foodName", ((TextView) v.findViewById(R.id.foodName)).getText().toString());
        startActivity(intent);
    }

    public void displayInfo(View v){
        Intent intent = new Intent(this, DisplayInfomation.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayStat(View v){
        displayStat();
    }

    public void backToPrevious(View v){
        Intent intent = new Intent(this, PopLogout.class);
        startActivity(intent);
    }
}