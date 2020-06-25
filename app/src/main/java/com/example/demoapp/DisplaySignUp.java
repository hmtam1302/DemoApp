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

import com.example.demoapp.DataControl.CustomerManager;
import com.example.demoapp.model.Customer;

import java.util.regex.Pattern;

public class DisplaySignUp extends AppCompatActivity {

    public static UserManager userManager = new UserManager();
    public static String sUserName = "";
    public static int ID = -1;
    private Scene signupScene = null;

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
            Customer customer = new Customer(userName, password, confirmPassword, phone, email);
            final CustomerManager cusMan = new CustomerManager(this);
            cusMan.addCustomer(customer);
            sUserName = userName;
            ID = customer.getID();
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
}
