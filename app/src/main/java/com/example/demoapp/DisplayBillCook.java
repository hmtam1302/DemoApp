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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DisplayBillCook extends AppCompatActivity {
    private Scene billCookScene;
    private Scene billCookDetailScene;

    private String selectedID;

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
        billCookDetailScene = Scene.getSceneForLayout(root, R.layout.display_bill_cook_detail, this);

        displayBillCook();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCook(){
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
        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                displayBillCookDetail(v);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCookDetail(View view) {
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCookDetailScene, slide);

        final ListView listView = (ListView) findViewById(R.id.list_bill_cook_item);

        //Set the title with Bill: ID
        TextView billID = (TextView) view.findViewById(R.id.bill_id);
        selectedID = billID.getText().toString();
        TextView title = (TextView) findViewById(R.id.bill_title);
        title.setText("Bill: " + selectedID);

        TextView priceView = (TextView) findViewById(R.id.total_price_bill_detail);
        TextView timeView = (TextView) findViewById(R.id.delivery_time_bill_detail);
        ImageView statusView = (ImageView) findViewById(R.id.status_bill_detail);
        ImageButton button = (ImageButton) findViewById(R.id.command_btn);

        Bill bill = DisplayCart.billManager.getBillByID(selectedID);

        listView.setAdapter(new CustomListBillItemAdapter(this, bill.billItemList));
        priceView.setText(bill.getTotalPrice() + "VND");
        timeView.setText(bill.getTime());

        if(bill.getStatus().equals("being_prepared")){
            statusView.setImageDrawable(getResources().getDrawable(R.drawable.being_prepared));
            button.setBackground(getResources().getDrawable(R.drawable.finish_button));
        }
        else if (bill.getStatus().equals("finished")){
            statusView.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            button.setBackground(getResources().getDrawable(R.drawable.ok_button));
        }
        else{
            statusView.setImageDrawable(getResources().getDrawable(R.drawable.unconfirmed));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void commandBill(View v){
        Bill bill = DisplayCart.billManager.getBillByID(selectedID);

        if(bill.getStatus().equals("unconfirmed")){
            bill.setStatus("being_prepared");
            displayBillCook();
        }
        else if (bill.getStatus().equals("being_prepared")){
            bill.setStatus("finished");

            //Send the bill to the end of Bill List
            DisplayCart.billManager.addToCompletedBillList(selectedID);
            displayBillCook();
        }
        else{
            displayBillCook();
        }
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
    public  void displayCompletedBill(View v){
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
        displayBillCook();
    }
}