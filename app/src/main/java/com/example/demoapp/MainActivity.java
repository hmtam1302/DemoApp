package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoapp.Data.Customer;
import com.example.demoapp.Data.Food;
import com.example.demoapp.Data.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String urlGetDataCustomer = "http://172.17.19.69:8080/androidwebservice/customer/getData.php";
    String urlGetDataRestaurant = "http://172.17.19.69:8080/androidwebservice/restaurant/getData.php";
    String urlGetDataFood = "http://172.17.19.69:8080/androidwebservice/food/getData.php";
    String urlGetDataOrder = "http://172.17.19.69:8080/androidwebservice/order/getData.php";
    public static ArrayList<Customer> cusList = new ArrayList<>();
    public static ArrayList<Restaurant> resList = new ArrayList<>();
    public static ArrayList<Food> foodList = new ArrayList<>();
    public static ArrayList<BillItem> orderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataCustomer(urlGetDataCustomer);
        getDataRestaurant(urlGetDataRestaurant);
        getDataFood(urlGetDataFood);
        getOrderFood(urlGetDataOrder);
        //Start into display activity
        Intent intent = new Intent(this, DisplayWelcome.class);
        startActivity(intent);
    }

    private void getDataCustomer(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", "Get Customer Array");
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int ID = object.getInt("ID");
                                String UserName = object.getString("UserName");
                                String PassWord = object.getString("PassWord");
                                String Name = object.getString("Name");
                                String DateOfBirth = object.getString("DateOfBirth");
                                int Gender = object.getInt("Gender");
                                String Email = object.getString("Email");
                                String Phone = object.getString("Phone");
                                String Role = object.getString("Role");
                                int RestaurantID = object.getInt("RestaurantID");
                                cusList.add(new Customer(ID, UserName, PassWord, Name, DateOfBirth, Gender, Email, Phone, Role, RestaurantID));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("msg", cusList.size()+"");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test", "-------------------");
                        Log.d("test", error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataRestaurant(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", "Get Restaurant Array");
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int ID = object.getInt("ID");
                                String Name = object.getString("Name");
                                String Logo = object.getString("Logo");
                                String Description = object.getString("Description");
                                String Rating = object.getString("Rating");
                                resList.add(new Restaurant(ID, Name, Logo, Description, Rating));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("msg", resList.size()+"");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test", "-------------------");
                        Log.d("test", error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataFood(String url) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("test", "Get Food Array");
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    int ID = object.getInt("ID");
                                    int Res_ID = object.getInt("Res_ID");
                                    String Name = object.getString("Name");
                                    String Logo = object.getString("Logo");
                                    int Quantity = object.getInt("Quantity");
                                    String Description = object.getString("Description");
                                    String Price = object.getString("Price");
                                    String Rating = object.getString("Rating");
                                    String Enable = object.getString("Enable");
                                    foodList.add(new Food(ID, Res_ID, Name, Logo, Quantity, Description, Price, Rating, Enable));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("msg", foodList.size()+"");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("test", "-------------------");
                            Log.d("test", error.toString());
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
        }
    }

    private void getOrderFood(String url) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("test", "Get Order Array");
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    int Order_ID = object.getInt("OrderID");
                                    int Cus_ID = object.getInt("CustomerID");
                                    int Res_ID = object.getInt("RestaurantID");
                                    int Food_ID = object.getInt("FoodID");
                                    String Name = object.getString("Name");
                                    String Quantity = object.getInt("Quantity")+"";
                                    String Description = object.getString("Description");
                                    String Price = object.getString("Price");
                                    String Status = object.getString("Status");
                                    orderList.add(new BillItem(Order_ID, Cus_ID, Res_ID, Food_ID, Name, Quantity, Description, Price, Status));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("msg", orderList.size()+"");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("test", "-------------------");
                            Log.d("test", error.toString());
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
        }
    }
}
