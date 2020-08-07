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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DisplayBillCookDetail extends AppCompatActivity {

    private Scene billCookDetailScene;
    private String selectedID;
    String urlUpdateData = "http://172.17.23.72:8080/androidwebservice/order/update.php";


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

            //Update database
            int ID = Integer.valueOf(selectedID);
            Log.d("check", MainActivity.orderList.size()+"");
            for(int i = 0; i < MainActivity.orderList.size(); i++) {
                Log.d("check ID", MainActivity.orderList.get(i).getID()+"");
                if(MainActivity.orderList.get(i).getID() == ID) {
                    update(urlUpdateData, ID, "being_prepared");
                    MainActivity.orderList.get(i).setStatus("being_prepared");
                }
            }
            displayBillCook();
        }
        else if (bill.getStatus().equals("being_prepared")){
            bill.setStatus("completed");

            // Update database
            int ID = Integer.valueOf(selectedID);
            for(int i = 0; i < MainActivity.orderList.size(); i++) {
                Log.d("check ID", MainActivity.orderList.get(i).getID()+"");
                if(MainActivity.orderList.get(i).getID() == ID) {
                    update(urlUpdateData, ID, "completed");
                    MainActivity.orderList.get(i).setStatus("completed");
                }
            }

            //Send the bill to the end of Bill List
            DisplayCart.billManager.addToCompletedBillList(selectedID);
            displayBillCook();
        }
        else{
            displayCompletedBillCook();
        }
    }

    public void backToPrevious(View v){finish();}

    private void update(String url, final int orderID, final String status) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "Update order's status successful");
                        } else {
                            Log.d("msg", "Update order's status fail");
                            Log.d("msg", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("msg", "Fault in database/link. "+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OrderID", String.valueOf(orderID));
                params.put("Status", status);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}