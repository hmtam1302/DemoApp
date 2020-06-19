package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class DisplayInfomation extends AppCompatActivity {
    private Scene informationScene = null;
    private Spinner genderSpinner = null;
    private Scene changepasswordScene = null;
    private Scene passwordScene = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide title bar
        //Hide title bar and enable full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        //Set main content view
        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.mainContainer);

        informationScene = Scene.getSceneForLayout(root, R.layout.accountinfo, this);
        changepasswordScene = Scene.getSceneForLayout(root, R.layout.changepassword, this);
        passwordScene = Scene.getSceneForLayout(root, R.layout.changepassword, this);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayInfo(View view){
        Transition explode = new Explode();
        TransitionManager.go(informationScene, explode);
        view.setBackgroundColor(Color.parseColor("#FEF8A6"));
        //Set up gender spinner
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        genderSpinner.setEnabled(false);
    }
    public void editPersonalInfo(View view){
        EditText name = (EditText)findViewById(R.id.name);
        EditText dateofbirth = (EditText)findViewById(R.id.dateofbirth);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phonenumber = (EditText)findViewById(R.id.phonenumber);

        ImageButton saveBtn = (ImageButton)findViewById(R.id.savebtn);

        //Set name, date of birth, gender, email, phone number enable
        name.setEnabled(true);
        dateofbirth.setEnabled(true);
        genderSpinner.setEnabled(true);
        email.setEnabled(true);
        phonenumber.setEnabled(true);
        //Set up save button
        saveBtn.setClickable(true);
        saveBtn.setImageResource(R.drawable.savebtn);
        //Set up edit button
        ImageButton editBtn = (ImageButton)view;
        editBtn.setClickable(false);
        editBtn.setImageResource(R.drawable.editbtndisable);
    }
    public void savePersonalInfo(View view){
        EditText name = (EditText)findViewById(R.id.name);
        EditText dateofbirth = (EditText)findViewById(R.id.dateofbirth);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phonenumber = (EditText)findViewById(R.id.phonenumber);

        ImageButton editBtn = (ImageButton)findViewById(R.id.editbtn);

        //Set name, date of birth, gender, email, phone number disable
        name.setEnabled(false);
        dateofbirth.setEnabled(false);
        genderSpinner.setEnabled(false);
        email.setEnabled(false);
        phonenumber.setEnabled(false);
        //Set up edit button
        editBtn.setClickable(true);
        editBtn.setImageResource(R.drawable.editbtn);
        //Set up save button
        ImageButton saveBtn = (ImageButton)view;
        saveBtn.setClickable(false);
        saveBtn.setImageResource(R.drawable.savebtndisable);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayChangePassword(View view){
        Transition explode = new Explode();
        TransitionManager.go(passwordScene, explode);
    }
}
