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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demoapp.Data.Restaurant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DisplayCook extends AppCompatActivity {
    private Scene billCookScene;
    private Scene billCookDetailScene;
    private Scene settingScene;

    private String selectedID;
    private String resName = "KFC"; //This variable is used to find which restaurant to display

    private ArrayList<Bill> prepareBillList = new ArrayList<>();
    private ArrayList<Bill> completedBillList = new ArrayList<>();
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
        setContentView(R.layout.cook_main);

        ViewGroup root = findViewById(R.id.mainContainer);
        billCookScene = Scene.getSceneForLayout(root, R.layout.bill_cook, this);
        billCookDetailScene = Scene.getSceneForLayout(root, R.layout.display_bill_cook_detail, this);
        settingScene = Scene.getSceneForLayout(root, R.layout.setting, this);

        //Set up prepateBillList and completedBillList
        if(count == 0){
            getBillList();
            DisplayCart.billManager.setBillList(prepareBillList);
            DisplayCart.billManager.setCompletedBillList(completedBillList);
        }

        String cmd = getIntent().getStringExtra("cmd");
        if (cmd != null) displayCompletedBill();
        else displayBillCook();
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
        TransitionManager.go(billCookScene, slide);

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
        ListView listView = (ListView)findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getCompletedBillList()));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }


    public void backToPrevious(View v){
        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCook(View v){
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

        //Display completed bill list
        ListView listView = (ListView) findViewById(R.id.bill_cook_List);
        listView.setAdapter(new CustomListBillCookAdapter(this, DisplayCart.billManager.getCompletedBillList()));
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displaySettings(View v){
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

        //Display food for settings
        ListView listView = (ListView) findViewById(R.id.list_food);

        //Find restaurant match given name
        List<Restaurant> resList = MainActivity.resList; //Get from database, ****** Cho nay fix roi nha
        Restaurant res = null;
        for(int i = 0; i < resList.size(); i++){
            res = resList.get(i);
            if (res.getName().equals(resName)){
                break;
            }
        }

        listView.setAdapter(new CustomListFoodAdapter(this, DisplayHome.foodList));
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
    }
}