package com.example.demoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class PopLogout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logoutpop);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int heigth = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(heigth*.22));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    public void confirmLogout(View view){
        //DisplaySignUp.sUserName = null;
        Intent intent = new Intent(this, DisplayWelcome.class);
        startActivity(intent);
    }
    public void cancleLogout(View view){
        Intent intent = new Intent(this, DisplayHome.class);
        startActivity(intent);
    }
}
