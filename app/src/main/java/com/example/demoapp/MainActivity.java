package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private Scene mainScene =null;
    private Scene loginScene = null;
    private Scene signupScene = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide title bar and enable full-screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.mainContainer);

        mainScene = Scene.getSceneForLayout(root, R.layout.main, this);
        loginScene = Scene.getSceneForLayout(root, R.layout.login, this);
        signupScene = Scene.getSceneForLayout(root, R.layout.signup, this);

        mainScene.enter();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loginActivity(View view) {
        TransitionManager.go(loginScene);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void backToMain(View view){
        Transition explode = new Explode();
        TransitionManager.go(mainScene, explode);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void signupActivity(View view){
        Transition explode = new Explode();
        TransitionManager.go(signupScene, explode);
    }
}
