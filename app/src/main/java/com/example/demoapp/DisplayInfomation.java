package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayInfomation extends AppCompatActivity {
    private Scene informationScene = null;
    private Spinner genderSpinner = null;
    private Scene changepasswordScene = null;
    private Scene passwordScene = null;
    //private UserData userData = DisplaySignUp.userManager.getData(DisplaySignUp.sUserName);
    private String userNameRef;
    private int userID = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        displayInfo();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayInfo(){
        Transition explode = new Explode();
        TransitionManager.go(informationScene, explode);
        //Set up gender spinner
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayList<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        genderSpinner.setEnabled(false);

        //Log.d("check: ", DisplayLogin.sUserName);
        //Log.d("Check empty: ", DisplayLogin.sUserName.isEmpty()+"");

        //Display information
        EditText edtUserName = (EditText)findViewById(R.id.edtUsername);
        //edtUserName.setText(customer.getUsername());

        EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
        //edtPassword.setText(customer.getPassword());

        EditText edtFullName = (EditText)findViewById(R.id.fullname);
        //edtFullName.setText(customer.getName());

        EditText edtDateofbirth = (EditText)findViewById(R.id.dateofbirth);
        //edtDateofbirth.setText(customer.getDateOfBirth());

        EditText edtPhone = (EditText)findViewById(R.id.phonenumber);
        //edtPhone.setText(customer.getPhoneNumber());

        EditText edtEmail = (EditText)findViewById(R.id.email);
        //edtEmail.setText(customer.getEmail());
    }
    public void editPersonalInfo(View view){
        EditText name = (EditText)findViewById(R.id.fullname);
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
        saveBtn.setBackgroundResource(R.drawable.savebtn);

        //Set up edit button
        ImageButton editBtn = (ImageButton)view;
        editBtn.setClickable(false);
        editBtn.setBackgroundResource(R.drawable.editbtndisable);
    }
    public void savePersonalInfo(View view){
        // Get userID
        EditText username = (EditText)findViewById(R.id.edtUsername);
        EditText password = (EditText)findViewById(R.id.edtPassword);
        // Get user data
        EditText name = (EditText)findViewById(R.id.fullname);
        Spinner gender = (Spinner) findViewById(R.id.genderSpinner);
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
        String nameChanging = name.getText().toString();
        String genderChanging = gender.getSelectedItem().toString();
        String dobChanging = dateofbirth.getText().toString();
        String emailChanging = email.getText().toString();
        String phoneChanging = phonenumber.getText().toString();
        int ID = -1;

        // Get username and password to get user ID
        String userName = username.getText().toString();
        String Password = password.getText().toString();

        // Update customer information in database
        /*CustomerManager cusMan = new CustomerManager(this);
        List<Customer> listCustomer = new ArrayList<>();
        listCustomer = cusMan.getAllCustomer();
        Customer customer = new Customer(userName, Password, Password, nameChanging, genderChanging, dobChanging, phoneChanging, emailChanging);
        for(int i = 0; i < listCustomer.size(); i++) {
            if(userName.equals(listCustomer.get(i).getUsername()) && Password.equals(listCustomer.get(i).getPassword())) {
                ID = listCustomer.get(i).getID();
                break;
            }
        }
        cusMan.updateCustomer(customer, ID); */

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
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(passwordScene, slide);
    }

    //edit and check new password function
    public void checkPassword(View view){
        EditText curPassword = (EditText)findViewById(R.id.curPassword);
        EditText newPassword = (EditText)findViewById(R.id.newPassword);
        EditText newPasswordconfirm = (EditText)findViewById(R.id.newPasswordconfirm);
        String userNameRef = "";
        String name = "";
        String password = "";
        String confirmPassword = "";
        String dateofbirth = "";
        String gender = "";
        String email = "";
        String phone = "";
        int ID = -1;

        // Get password input from user
        String currentPass = curPassword.getText().toString();
        String newPass = newPassword.getText().toString();
        String confirmNewPass = newPasswordconfirm.getText().toString();

        //Step 1: check curPassword is userData.password
        /*
        CustomerManager cusMan = new CustomerManager(this);
        List<Customer> listCustomer = new ArrayList<>();
        listCustomer = cusMan.getAllCustomer();
        for(int i = 0; i < listCustomer.size(); i++) {
            if(userNameRef.equals(listCustomer.get(i).getUsername()) && ID == listCustomer.get(i).getID()) {
                ID = listCustomer.get(i).getID();
                name = listCustomer.get(i).getName();
                password = listCustomer.get(i).getPassword();
                confirmPassword = listCustomer.get(i).getConfirmPassword();
                dateofbirth = listCustomer.get(i).getDateOfBirth();
                gender = listCustomer.get(i).getGender();
                email = listCustomer.get(i).getEmail();
                phone = listCustomer.get(i).getPhoneNumber();
                break;
            }
        }
        Customer customer = new Customer(userNameRef, password, confirmPassword, name, gender, dateofbirth, phone, email);
        Log.d("data: ", password);
        Log.d("input: ", currentPass);
        if(!password.equals(currentPass)) {
            //Display wrong message
            Log.d("data: ", password);
            Log.d("input: ", currentPass);
            TextView messageTxt = (TextView)findViewById(R.id.wrong_message);
            messageTxt.setText("Enter wrong current password! Try again!");
            return;
        }
        //Step 2: check newPassword is same newPasswordconfirmed
        else {
            Log.d("data: ", newPass);
            Log.d("input: ", confirmNewPass);
            if (newPass.equals(confirmNewPass)) {
                customer.setPassword(newPass);
                customer.setConfirmPassword(confirmNewPass);
                cusMan.updateCustomer(customer, ID);
                // Add message to notify the change is successful




                return;
            } else {
                //Display wrong message
                TextView messageTxt = (TextView) findViewById(R.id.wrong_message);
                messageTxt.setText("Enter wrong confirm password! Try again!");
                return;
            }
        }
         */
    }
    public void backToPrevious(View view){
        finish();
    }
}
