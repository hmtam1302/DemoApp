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

public class PopSuccessActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notipop);

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

    public void displayHome(View view){
        Intent intent;
        String role = getIntent().getStringExtra("role");
        if (role.equals("vendor")){
            intent = new Intent(this, DisplayCook.class);
        }
        else{
            intent = new Intent(this, DisplayHome.class);
        }
        startActivity(intent);
    }
}
