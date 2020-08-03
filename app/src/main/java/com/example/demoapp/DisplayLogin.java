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

import com.example.demoapp.Data.Customer;
import com.example.demoapp.Data.Food;
import com.example.demoapp.Data.Restaurant;

import java.util.ArrayList;


public class DisplayLogin extends AppCompatActivity {
    private Scene loginScene = null;
    public static Customer currentUser = null;

    public static ArrayList<Customer> cusList = MainActivity.cusList;
    public static ArrayList<Restaurant> resList = MainActivity.resList;
    public static ArrayList<Food> foodList = MainActivity.foodList;
    public static ArrayList<BillItem> orderList = MainActivity.orderList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        boolean accessCus = false;
        boolean accessCook = false;
        boolean accessVendor = false;
        boolean accessManager = false;
        EditText user_name = (EditText)findViewById(R.id.loginUsername);
        EditText pass_word = (EditText)findViewById(R.id.loginPassword);
        String username = user_name.getText().toString();
        String password = pass_word.getText().toString();
        for(int i = 0; i < cusList.size(); i++) {
            if(username.equals(cusList.get(i).getUsername()) && password.equals(cusList.get(i).getPassWord()) && cusList.get(i).getRole().equals("user")){
                accessCus = true;
                currentUser = cusList.get(i);
                break;
            } else if(username.equals(cusList.get(i).getUsername()) && password.equals(cusList.get(i).getPassWord()) && cusList.get(i).getRole().equals("cook")) {
                accessCook = true;
                currentUser = cusList.get(i);
                break;
            } else if(username.equals(cusList.get(i).getUsername()) && password.equals(cusList.get(i).getPassWord()) && cusList.get(i).getRole().equals("vendor")) {
                accessVendor = true;
                currentUser = cusList.get(i);
                break;
            }
            else if(username.equals(cusList.get(i).getUsername()) && password.equals(cusList.get(i).getPassWord()) && cusList.get(i).getRole().equals("manager")) {
                accessManager = true;
                currentUser = cusList.get(i);
                break;
            }
        }

        if(accessCus) {
            Intent intent = new Intent(this, PopSuccessActivity.class);
            intent.putExtra("role", "customer");
            startActivity(intent);
        } else if(accessCook)  { //Còn access vendor với manager nữa
            Log.d("msg", "vendor login");
            Intent intent = new Intent(this, PopSuccessActivity.class);
            intent.putExtra("role", "vendor");
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
}
