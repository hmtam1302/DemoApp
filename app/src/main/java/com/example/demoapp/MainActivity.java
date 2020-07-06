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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String urlGetData = "http://192.168.0.102/androidwebservice/demo.php";
    ArrayList<Customer> cusList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cusList = new ArrayList<>();
        getData(urlGetData);
        //Start into display activity
        Intent intent = new Intent(this, DisplayWelcome.class);
        startActivity(intent);
    }

    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("test", "generate");
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT);
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        //int ID = object.getInt("ID");
                        int ID = i + 1;
                        String UserName = object.getString("UserName");
                        String PassWord = object.getString("PassWord");
                        String Name = object.getString("Name");
                        Log.d("test", "name " + Name);
                        String DateOfBirth = object.getString("DateOfBirth");
                        int Gender = object.getInt("Gender");
                        String Email = object.getString("Email");
                        String Phone = object.getString("Phone");
                        cusList.add(new Customer(ID, UserName, PassWord, Name, DateOfBirth, Gender, Email, Phone));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "-------------------");
                Log.d("test", error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT);
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
