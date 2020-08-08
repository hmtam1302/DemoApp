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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DisplayBill extends AppCompatActivity {

    private Scene billScene;
    String urlInsertData = "http://192.168.0.101//androidwebservice/order/insert.php";
    String urlUpdateData = "http://192.168.0.101/androidwebservice/food/update.php";


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
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(billScene, slide);

        ListView listView = (ListView)findViewById(R.id.bill_list);
        listView.setAdapter(new CustomListBillItemAdapter(this, DisplayCart.billManager.getBill().billItemList));

        TextView totalPriceTxt = (TextView)findViewById(R.id.bill_totalPrice);
        totalPriceTxt.setText(DisplayCart.billManager.getBill().getTotalPrice() + "VND");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addBillAndBack(View v){
        Bill bill = DisplayCart.billManager.getBill();

        //Generate Bill ID based on current date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();

        bill.setBillID(dtf.format(now).toString());


        //Set bill status
        bill.setStatus("unconfirmed");

        //Set bill delivery time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10 + DisplayCart.billManager.getBillList().size()*1);
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(calendar.getTime());
        Log.d("date", timeStamp);

        //Add new bill to database
        DisplayCart.billManager.addNewBill(bill);

        //Clear bill and return home
        DisplayCart.billManager.setNewBill();

        // Add bill to database
        Log.d("msg", bill.billItemList.size()+"");
        for(int i = 0; i < bill.billItemList.size(); i++) {
            bill.billItemList.get(i).setTime(timeStamp);
            Log.d("msg", bill.billItemList.get(i).getName());
            Log.d("msg", bill.billItemList.get(i).getQuantity()+"");
            addData(urlInsertData, bill.billItemList.get(i));
        }
        DisplayHome.num_of_order++;
        Log.d("food size", DisplayHome.foodList.size()+"");
        Log.d("item size", bill.billItemList.size()+"");

        for(int i = 0; i < DisplayHome.foodList.size(); i++) {
            for(int j = 0; j < bill.billItemList.size(); j++) {
                if(DisplayHome.foodList.get(i).getRes_ID() == bill.billItemList.get(j).getRestaurantID()) {
                    if(DisplayHome.foodList.get(i).getID() == bill.billItemList.get(j).getFoodID()) {
                        int newQuantity = DisplayHome.foodList.get(i).getQuantity() - Integer.valueOf(bill.billItemList.get(j).getQuantity());
                        Log.d("old quantity", DisplayHome.foodList.get(i).getQuantity()+"");
                        Log.d("sub", bill.billItemList.get(j).getQuantity());
                        Log.d("new quantity", newQuantity+"");
                        DisplayHome.foodList.get(i).setQuantity(newQuantity);
                        update(urlUpdateData, DisplayHome.foodList.get(i).getID(), newQuantity);
                    }
                }
            }
        }

        bill.billItemList.clear();

        //Display popup successfull and back for order new bill
        Intent intent = new Intent(this, PopSuccessActivity.class);
        intent.putExtra("role", "customer");
        startActivity(intent);

        //Here for test bill, when done, add new method to display bill on cook account
        //Intent intent = new Intent(this, DisplayCook.class);
        //startActivity(intent);
    }

    public void backToPrevious(View view){
        finish();
    }

    private void addData(String url, final BillItem item) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "add new order's item successfully");
                        } else {
                            Log.d("msg", "add new order's item failure");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("msg","Loi server/link " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OrderID", item.getID()+"");
                params.put("CustomerID", item.getCustomerID()+"");
                params.put("RestaurantID", item.getRestaurantID()+"");
                params.put("FoodID", item.getFoodID()+"");
                params.put("Name", item.getName());
                params.put("Quantity", item.getQuantity()+"");
                params.put("Description", item.getDescription());
                params.put("Price", item.getPrice());
                params.put("Status", item.getStatus());
                params.put("Time", item.getTime());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void update(String url, final int id, final int quantity) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "Update food quantity successful");
                        } else {
                            Log.d("msg", "Update food quantity fail");
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
                params.put("idFood", id+"");
                params.put("quantityFood", quantity+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
