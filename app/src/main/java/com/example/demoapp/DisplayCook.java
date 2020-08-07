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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demoapp.Data.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DisplayCook extends AppCompatActivity {
    private Scene billCookScene;
    private Scene billCompletedScene;
    private Scene settingScene;

    private String selectedID;
    private int resID = DisplayLogin.currentUser.getRestaurantID();   //Get current restaurant of the cook
    private ArrayList<Food> listFood;

    private static ArrayList<Bill> prepareBillList;
    private static ArrayList<Bill> completedBillList;

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
        setContentView(R.layout.cook_main);

        ViewGroup root = findViewById(R.id.mainContainer);
        billCookScene = Scene.getSceneForLayout(root, R.layout.bill_cook, this);
        billCompletedScene = Scene.getSceneForLayout(root, R.layout.bill_completed_cook, this);
        settingScene = Scene.getSceneForLayout(root, R.layout.setting, this);

        //Set up prepateBillList and completedBillList
        prepareBillList = new ArrayList<>();
        completedBillList = new ArrayList<>();
        getBillList();
        getFoodList();
        DisplayCart.billManager.setBillList(prepareBillList);
        DisplayCart.billManager.setCompletedBillList(completedBillList);


        String cmd = getIntent().getStringExtra("cmd");
        if (cmd != null) {
            if(cmd.equals("foodsetting")) displayCookSettings();
            else displayCompletedBill();
        }
        else displayBillCook();

    }

    private void getBillList() {
        ArrayList<BillItem> tempList = new ArrayList<>();
        tempList.addAll(DisplayLogin.orderList);
        int countItem = 0;
        int countBill = 0;
        while (tempList.size() != 0) {
            Bill bill = new Bill();
            if (tempList.get(0).getRestaurantID() == resID) {
                countBill++; countItem++;
                bill.addNewItem(tempList.get(0));      //Add the first element in the orderList
                String status = tempList.get(0).getStatus();
                int id = tempList.get(0).getID();
                bill.setStatus(status);
                bill.setBillID(Integer.toString(id));
                tempList.remove(0);            //then remove it and set the status of the bill

                //Add bill item with the same ID and restaurantID
                int i = 0;
                while (i < tempList.size()) {
                    if (tempList.get(i).getID() == id) {
                        countItem++;
                        bill.addNewItem(tempList.get(i));
                        tempList.remove(i);
                        i--;
                    }
                    i++;
                }

                //Add bill to prepareBillList or completedBillList
                bill.calcTotalPrice();  //Calculate total price of bill
                if (!status.equals("completed")) {
                    prepareBillList.add(bill);
                } else {
                    completedBillList.add(bill);
                }
            } else tempList.remove(0);
        }
    }

    public void getFoodList(){
        //Get the food list of the restaurant
       listFood = new ArrayList<>();
        for(int i = 0; i < DisplayLogin.foodList.size(); i++){
            Food food = DisplayLogin.foodList.get(i);
            if(food.getRes_ID() == resID) listFood.add(food);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCook(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCookScene, slide);

        //Set navigation bar
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Waiting Bill");
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing_pressed));
        listButton.setTag(R.drawable.preparing_pressed); //Set tag for searching
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete));
        completedButton.setTag(null);   //Set tag for searching
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        ListView listView = (ListView)findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getBillList()));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }

    public void displayBillCookDetail(View view) {
        //Set the title with Bill: ID
        TextView billID = (TextView) view.findViewById(R.id.bill_id);
        selectedID = billID.getText().toString();

        Intent intent = new Intent(this, DisplayBillCookDetail.class);
        intent.putExtra("Bill_ID", selectedID);
        startActivity(intent);
    }

    public void searchBill(View v){
        EditText searchEdt = (EditText) findViewById(R.id.billKey);
        String billKey = searchEdt.getText().toString();

        //Determine what list bill is display
        ImageButton billBtn = (ImageButton) findViewById(R.id.list_button);
        Integer resource = (Integer) billBtn.getTag();
        List<Bill> billList;
        if (resource != null) {
            billList = DisplayCart.billManager.getBillList();
        }
        else{
            billList = DisplayCart.billManager.getCompletedBillList();
        }

        List<Bill> billResList = new ArrayList<>();

        if (billKey != null) {
            for (int i = 0; i < billList.size(); i++) {
                Bill bill = billList.get(i);
                if (bill.getBillID().contains(billKey)) billResList.add(bill);
            }

            //Display result of search
            ListView listView = (ListView) findViewById(R.id.bill_cook_List);
            listView.setAdapter(new CustomListBillCookAdapter(this, billResList));
        }
        else{
            //Display result of search
            ListView listView = (ListView) findViewById(R.id.bill_cook_List);
            listView.setAdapter(new CustomListBillCookAdapter(this, billList));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void displayCompletedBill(){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCompletedScene, slide);

        //Set navigation bar
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Completed Bill");
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing));
        listButton.setTag(null);    //Set tag for searching
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete_pressed));
        completedButton.setTag(R.drawable.complete_pressed); //Set tag for searching
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        //Display completed bill list
        ListView listView = (ListView)findViewById(R.id.bill_completed_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getCompletedBillList()));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }


    public void backToPrevious(View v){
        Intent intent = new Intent(this, PopLogout.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCook(View v){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCookScene, slide);

        //Set navigation bar
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing_pressed));
        listButton.setTag(R.drawable.preparing_pressed); //Set tag for searching
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete));
        completedButton.setTag(null);   //Set tag for searching
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));

        ListView listView = (ListView)findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getBillList()));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayCompletedBill(View v){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCompletedScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Completed Bill");
        //Set navigation bar
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing));
        listButton.setTag(null);    //Set tag for searching
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete_pressed));
        completedButton.setTag(R.drawable.complete_pressed); //Set tag for searching
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        //Display completed bill list
        ListView listView = (ListView) findViewById(R.id.bill_completed_cook_List);
        ArrayList<Bill> completedBillList = DisplayCart.billManager.getCompletedBillList();
        listView.setAdapter(new CustomListBillCookAdapter(this, completedBillList));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayCookSettings(View v){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");
        //Set navigation bar
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing));
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete));
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
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
    public void displayCookSettings() {
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(settingScene, slide);

        //Set title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Settings");
        //Set navigation bar
        ImageButton listButton = (ImageButton) findViewById(R.id.list_button);
        listButton.setImageDrawable(getResources().getDrawable(R.drawable.preparing));
        ImageButton completedButton = (ImageButton) findViewById(R.id.completed_button);
        completedButton.setImageDrawable(getResources().getDrawable(R.drawable.complete));
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        settingButton.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));
        ImageButton infoButton = (ImageButton) findViewById(R.id.info_btn);
        infoButton.setImageDrawable(getResources().getDrawable(R.drawable.account_btn));

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_food);

        if(listFood == null) getFoodList();
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
}