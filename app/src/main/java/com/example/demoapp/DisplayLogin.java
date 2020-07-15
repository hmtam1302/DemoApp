package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

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

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class DisplayLogin extends AppCompatActivity {
    private Scene loginScene = null;
    public static Customer customerLogin = null;
    String urlGetData = "http://192.168.1.56/androidwebservice/getData.php";
    public static ArrayList<Customer> cusList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(urlGetData);
        Log.d("msg", cusList.size()+"");
        //Hide title bar and enable full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.mainContainer);

        loginScene = Scene.getSceneForLayout(root, R.layout.login, this);
        loginActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loginActivity() {
        TransitionManager.go(loginScene);
    }
    public void displayLoginPop(View view){
        boolean access = false;
        EditText user_name = (EditText)findViewById(R.id.loginUsername);
        EditText pass_word = (EditText)findViewById(R.id.loginPassword);
        String username = user_name.getText().toString();
        String password = pass_word.getText().toString();
        Log.d("check", cusList.size()+"");
        for(int i = 0; i < cusList.size(); i++) {
            Log.d("name: ", cusList.get(i).getUsername());
            Log.d("pass ", cusList.get(i).getPassWord());
            if(username.equals(cusList.get(i).getUsername()) && password.equals(cusList.get(i).getPassWord())){
                access = true;
                customerLogin = cusList.get(i);
                Log.d("Login. ID = ", customerLogin.getID()+"");
                break;
            }
        }

        if(access) {
            TextView message = (TextView)findViewById(R.id.message);
            message.setText("");
            Intent intent = new Intent(this, PopSuccessActivity.class);
            startActivity(intent);
        } else {
            TextView message = (TextView)findViewById(R.id.message);
            message.setText("Wrong username or password");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToMain(View view){
        Intent intent = new Intent(this, DisplayWelcome.class);
        startActivity(intent);
    }
    public void signupActivity(View view){
        Intent intent = new Intent(this, DisplaySignUp.class);
        startActivity(intent);
    }
    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("test", "generate");
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int ID = object.getInt("ID");
                                String UserName = object.getString("UserName");
                                String PassWord = object.getString("PassWord");
                                String Name = object.getString("Name");
                                Log.d("test", "id: " + ID+"");
                                String DateOfBirth = object.getString("DateOfBirth");
                                int Gender = object.getInt("Gender");
                                String Email = object.getString("Email");
                                String Phone = object.getString("Phone");
                                cusList.add(new Customer(ID, UserName, PassWord, Name, DateOfBirth, Gender, Email, Phone));
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
}
