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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoapp.Data.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayInfomation extends AppCompatActivity {
    private Scene informationScene = null;
    private Spinner genderSpinner = null;
    private Scene changepasswordScene = null;
    private Scene passwordScene = null;
    private Customer customer = null;
    private int ID = -1;
    ArrayList<Customer> cusList = new ArrayList<>();
    String urlUpdateData = "http://192.168.0.101/androidwebservice/update.php";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get customer list from database
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
        genderList.add("Female");
        genderList.add("Male");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        genderSpinner.setEnabled(false);

        // Customer login or sign-up
        customer = DisplayLogin.customerLogin;
        if(DisplayLogin.customerLogin != null) {
            customer = DisplayLogin.customerLogin;
        } else {
            customer = DisplaySignUp.customerSignUp;
        }
        Log.d("msg check", customer.getUsername());
        //Display information
        EditText edtUserName = (EditText)findViewById(R.id.edtUsername);
        edtUserName.setText(customer.getUsername());

        EditText edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtPassword.setText(customer.getPassWord());

        EditText edtFullName = (EditText)findViewById(R.id.fullname);
        edtFullName.setText(customer.getName());

        genderSpinner.setSelection(customer.getGender());
        Log.d("check", customer.getGender()+"");

        EditText edtDateofbirth = (EditText)findViewById(R.id.dateofbirth);
        edtDateofbirth.setText(customer.getDateOfBirth());

        EditText edtPhone = (EditText)findViewById(R.id.phonenumber);
        edtPhone.setText(customer.getPhone());

        EditText edtEmail = (EditText)findViewById(R.id.email);
        edtEmail.setText(customer.getEmail());
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
        gender.setEnabled(false);
        email.setEnabled(false);
        phonenumber.setEnabled(false);

        // Get user input data
        String nameChanging = name.getText().toString();
        int genderChanging = gender.getSelectedItemPosition();
        Log.d("Check gender: ", genderChanging+"");
        String dobChanging = dateofbirth.getText().toString();
        String emailChanging = email.getText().toString();
        String phoneChanging = phonenumber.getText().toString();

        // Update customer information in database
        Log.d("msg pass: ", customer.getPassWord());
        Customer updateCustomer = new Customer(customer.getID(), customer.getPassWord(), nameChanging, dobChanging, genderChanging, emailChanging, phoneChanging);
        update(urlUpdateData, updateCustomer);
        //Set up edit button
        editBtn.setClickable(true);
        editBtn.setImageResource(R.drawable.editbtn);
        //Set up save button
        ImageButton saveBtn = (ImageButton)view;
        saveBtn.setClickable(false);
        saveBtn.setImageResource(R.drawable.savebtndisable);

        // Update customer local variable
        customer.setName(nameChanging);
        customer.setGender(genderChanging);
        customer.setDateOfBirth(dobChanging);
        customer.setPhone(phoneChanging);
        customer.setEmail(emailChanging);
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

        // Get password input from user
        String password = customer.getPassWord();
        String currentPass = curPassword.getText().toString();
        String newPass = newPassword.getText().toString();
        String confirmNewPass = newPasswordconfirm.getText().toString();

        //Step 1: check curPassword is userData.password
        if(!password.equals(currentPass)) {
            //Display wrong message
            Log.d("database pass: ", password);
            Log.d("user input pass: ", currentPass);
            TextView messageTxt = (TextView)findViewById(R.id.wrong_message);
            messageTxt.setText("Enter wrong current password! Try again!");
            return;
        }
        //Step 2: check newPassword is same newPasswordconfirmed
        else {
            Log.d("user input 1: ", newPass);
            Log.d("user input 2: ", confirmNewPass);
            if (newPass.equals(confirmNewPass)) {
                customer.setPassWord(newPass);
                update(urlUpdateData, customer);
                // Message to notify the change is successful
                Intent intent = new Intent(this, PopLoginAgain.class);
                startActivity(intent);
            } else {
                //Display wrong message
                TextView messageTxt = (TextView) findViewById(R.id.wrong_message);
                messageTxt.setText("Enter wrong confirm password! Try again!");
                return;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToPrevious(View view){
        finish();
    }

    private void update(String url, final Customer customerChange) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "Update successful");
                        } else {
                            Log.d("msg", "Update fail");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("msg", "Fault in database/link. "+error.toString());
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCus",String.valueOf(customerChange.getID()));
                params.put("passCus", customerChange.getPassWord());
                params.put("nameCus", customerChange.getName());
                params.put("dateCus", customerChange.getDateOfBirth());
                params.put("genderCus", String.valueOf(customerChange.getGender()));
                params.put("emailCus", customerChange.getEmail());
                params.put("phoneCus", customerChange.getPhone());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
