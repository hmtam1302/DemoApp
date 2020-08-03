package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.demoapp.Data.Food;
import com.example.demoapp.Data.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DisplaySignUp extends AppCompatActivity {
    public static Customer customerSignUp = null;
    private Scene signupScene = null;

    String urlInsertData = "http://192.168.0.102:8080/androidwebservice/customer/insert.php";
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

        //Set main content view
        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.mainContainer);

        signupScene = Scene.getSceneForLayout(root, R.layout.signup, this);
        displaySignup();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displaySignup() {
        Transition explode = new Explode();
        TransitionManager.go(signupScene, explode);
    }

    public void createAccount(View view) {
        int ID = cusList.get(cusList.size() - 1).getID() + 1;
        Log.d("check ID addin", ID+"");
        String userName = ((EditText) findViewById(R.id.usernameEdt)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEdt)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPasswordEdt)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneEdt)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailEdt)).getText().toString();

        Log.d("pass:", password);
        Log.d("pass confirm", confirmPassword);
        String message = check(userName, password, confirmPassword, email, phone);

        TextView messageTxt = (TextView) findViewById(R.id.messageSignup);
        messageTxt.setText(message);

        if (password.equals(confirmPassword) && message == null) {
            addData(urlInsertData);
            customerSignUp = new Customer(ID, userName, password, email, phone, "User");
            DisplayLogin.currentUser = customerSignUp;
            Intent intent = new Intent(this, PopSuccessActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToMain(View view) {
        Intent intent = new Intent(this, DisplayWelcome.class);
        startActivity(intent);
    }

    public void loginActivity(View view) {
        Intent intent = new Intent(this, DisplayLogin.class);
        startActivity(intent);
    }

    String check(String userName, String password, String confirmPassword, String email, String phone) {
        //Check userName
        String regex_username = "^[aA-zZ]\\w{5,29}$";
        String regex_mail = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        for (int i = 0; i < userName.length(); i++) {
            Pattern pattern = Pattern.compile(regex_username);
            if (!pattern.matcher(userName).matches()) {
                return "Username contains a-z, A-Z and 0-9 only!";
            }
        }

        //Check password
        if (!password.equals(confirmPassword)) {
            return "Wrong confirm password!";
        } else {
            //Check email
            Pattern pattern = Pattern.compile(regex_mail);
            if (!pattern.matcher(email).matches()) {
                return "Wrong email format!";
            } else {
                //Check phone
                if (phone.length() != 10 || phone.charAt(0) != '0') {
                    return "Phone number has exactly 10 digits";
                }
                return null;
            }
        }
    }

    private void addData(String url) {
        final String userName = ((EditText) findViewById(R.id.usernameEdt)).getText().toString();
        final String password = ((EditText) findViewById(R.id.passwordEdt)).getText().toString();
        final String phone = ((EditText) findViewById(R.id.phoneEdt)).getText().toString();
        final String email = ((EditText) findViewById(R.id.emailEdt)).getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "add new customer successfully");
                        } else {
                            Log.d("msg", "add new customer failure");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("msg","Loi server/link " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UsernameCus",userName);
                params.put("PasswordCus",password);
                params.put("PhoneCus", phone);
                params.put("EmailCus", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
