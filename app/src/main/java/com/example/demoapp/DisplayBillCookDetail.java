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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayBillCookDetail extends AppCompatActivity {

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
        billCookDetailScene = Scene.getSceneForLayout(root, R.layout.display_bill_cook_detail, this);

        selectedID = getIntent().getStringExtra("Bill_ID");

        displayBillCookDetail();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayBillCookDetail() {
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billCookDetailScene, slide);

        final ListView listView = (ListView) findViewById(R.id.list_bill_cook_item);

        //Set the title with Bill: ID
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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCompletedBillCook();
                }
            });
        }
        else{
            statusView.setImageDrawable(getResources().getDrawable(R.drawable.unconfirmed));
        }
    }



    public void displayBillCook(){
        Intent intent = new Intent(this, DisplayCook.class);
        startActivity(intent);
    }

    public void displayCompletedBillCook(){
        Intent intent = new Intent(this, DisplayCook.class);
        intent.putExtra("cmd", "complete");
        startActivity(intent);
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
            displayCompletedBillCook();
        }
    }

    public void backToPrevious(View v){finish();}
}