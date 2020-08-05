package com.example.demoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayHome(View view){
        Intent intent = null;
        String role = getIntent().getStringExtra("role");
        String cmd = getIntent().getStringExtra("cmd");
        if(role != null){
            if (role.equals("cook")) {
                intent = new Intent(this, DisplayCook.class);
            } else if (role.equals("vendor")) {
                intent = new Intent(this, DisplayVendor.class);
            } else if (role.equals("manager")) {
                intent = new Intent(this, DisplayManager.class);
            }
        } else if(cmd != null){
            if(cmd.equals("reportStall")){
                intent = new Intent(this, DisplayVendor.class);
            }
            else{
                intent = new Intent(this, DisplayManager.class);
            }
        }
        else{
            intent = new Intent(this, DisplayHome.class);
        }
        startActivity(intent);
    }
}
