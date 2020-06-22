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
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.EditText;
        import android.widget.TextView;

public class DisplaySignUp extends AppCompatActivity {

    public static UserManager userManager = new UserManager();
    public static String sUserName;
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
        signupScene.enter();
    }

    public void createAccount(View view) {

        String userName = ((EditText) findViewById(R.id.usernameEdt)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEdt)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPasswordEdt)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phoneEdt)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailEdt)).getText().toString();

        String message = check(userName, password, confirmPassword, email, phone);

        TextView messageTxt = (TextView) findViewById(R.id.messageSignup);
        messageTxt.setText(message);

        if (password.equals(confirmPassword) && message == null) {
            userManager.addData(userName, password, email, phone);
            sUserName = userName;
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


    //Method to check userName, password, confirmPassword, email and phone number.
    String check(String userName, String password, String confirmPassword, String email, String phone) {
        //Check userName
        String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        for (int i = 0; i < userName.length(); i++) {
            if (!(alphabet.contains(String.valueOf(userName.charAt(i))))) {
                return "Username contains a-z, A-Z and 0-9 only!";
            }
        }

        /*
        //Check password has the length more than 6 characters.
        String uniqueCharacter ="!@#$%^&*";
        if(password.length() < 6){
            return "Your password needs at least 6 characters";
        }

        //Check password has at least 1 unique character.
        int i;
        for(i = 0; i<password.length(); i++){
            if((uniqueCharacter.contains(String.valueOf(password.charAt(i))))){
                return null;
            }
        }
        if(i==password.length()){
            return "Your password needs at least 1 unique character.";
        }
         */

        //Check confirmed password is new password
        if (!password.equals(confirmPassword)) {
            return "Wrong confirm password!";
        } else {
            //Check email
            if (!email.contains(String.valueOf('@')) || !email.contains(".com")) {
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
