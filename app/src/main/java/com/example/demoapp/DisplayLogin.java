package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
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
import android.widget.Toast;

import com.example.demoapp.DataControl.CustomerManager;
import com.example.demoapp.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DisplayLogin extends AppCompatActivity {
    private Scene loginScene = null;
    public static String sUserName = "";
    public static int ID = -1;

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
        EditText user_name = (EditText)findViewById(R.id.loginUsername);
        EditText pass_word = (EditText)findViewById(R.id.loginPassword);
        String username = user_name.getText().toString();
        String password = pass_word.getText().toString();
        boolean access = false;
        final CustomerManager cusMan = new CustomerManager(this);
        List<Customer> listCustomer = new ArrayList<>();
        listCustomer = cusMan.getAllCustomer();
        Log.d("name: ", username);
        Log.d("pass ", password);

        for(int i = 0; i < listCustomer.size(); i++) {
            Log.d("username: ", listCustomer.get(i).getUsername());
            Log.d("password ", listCustomer.get(i).getPassword());
            if(username.equals(listCustomer.get(i).getUsername()) && password.equals(listCustomer.get(i).getPassword())) {
                access = true;
                sUserName = username;
                ID = listCustomer.get(i).getID();
                break;
            }
        }
        Log.d("access ", access+"");
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
}
