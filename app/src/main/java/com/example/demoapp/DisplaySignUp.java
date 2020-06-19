package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.EditText;

public class DisplaySignUp extends AppCompatActivity {

    private Scene signupScene = null;
    public static UserManager userManager = new UserManager();
    public static String sUserName;

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

        signupScene = Scene.getSceneForLayout(root, R.layout.signup, this);
        displaySignup();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displaySignup(){
        Transition explode = new Explode();
        TransitionManager.go(signupScene, explode);
    }

    public void createAccount(View view){

        String userName = ((EditText)findViewById(R.id.usernameEdt)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEdt)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPasswordEdt)).getText().toString();
        String phone = ((EditText)findViewById(R.id.phoneEdt)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailEdt)).getText().toString();

        if(password.equals(confirmPassword)){
            userManager.addData(userName, password, email, phone);
            sUserName = userName;
            Intent intent = new Intent(this, PopUpActivity.class);
            startActivity(intent);
        }
    }
}
