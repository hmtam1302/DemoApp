package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class DisplayLogin extends AppCompatActivity {

    private Scene loginScene = null;

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
        EditText userName = (EditText)findViewById(R.id.loginUsername);
        EditText passWord = (EditText)findViewById(R.id.loginPassword);

        UserData userData = DisplaySignUp.userManager.getData(userName.getText().toString());
        if(userData != null && userData.getPassword().equals(passWord.getText().toString())){
            Intent intent = new Intent(this, PopUpActivity.class);
            DisplaySignUp.sUserName = userName.getText().toString();
            startActivity(intent);
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
