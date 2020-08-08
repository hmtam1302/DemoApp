package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoapp.Data.Food;
import com.example.demoapp.Data.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class DisplayRestaurantSetting extends AppCompatActivity {
    private Restaurant selectedRes;
    private Scene resSettingScene;
    String urlUpdateData = "http://192.168.0.101/androidwebservice/restaurant/update.php";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        resSettingScene = Scene.getSceneForLayout(root, R.layout.restaurant_setting, this);
        displayRestaurantSetting(getIntent().getStringExtra("resName"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayRestaurantSetting(String resName){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(resSettingScene, slide);

        selectedRes = null;
        for(int i = 0; i < DisplayLogin.resList.size(); i++){
            selectedRes = DisplayLogin.resList.get(i);
            if(resName.equals(selectedRes.getName())) break;
        }

        //Set value for restaurant setting scene
        ImageView logoView = (ImageView) findViewById(R.id.res_logo);
        logoView.setImageResource(getMipmapResIdByName(selectedRes.getLogo()));
        EditText nameEdt = (EditText) findViewById(R.id.edt_res_name);
        nameEdt.setText(selectedRes.getName());
        EditText descriptionEdt = (EditText) findViewById(R.id.edt_res_discription);
        descriptionEdt.setText(selectedRes.getDescription());
        EditText ratingEdt = (EditText) findViewById(R.id.edt_res_ratings);
        ratingEdt.setText(selectedRes.getRating());

    }

    public int getMipmapResIdByName(String logo)  {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(logo , "mipmap", pkgName);
        return resID;
    }


    //When comfirm change, also update on database
    public void confirmChange(View v){
        //Get value of changes
        EditText nameEdt = (EditText) findViewById(R.id.edt_res_name);
        selectedRes.setName(nameEdt.getText().toString());
        EditText ratingEdt = (EditText) findViewById(R.id.edt_res_ratings);
        selectedRes.setRating(ratingEdt.getText().toString());
        EditText descriptionEdt = (EditText) findViewById(R.id.edt_res_discription);
        selectedRes.setDescription(descriptionEdt.getText().toString());

        // Update database
        update(urlUpdateData, selectedRes.getID(), selectedRes.getName(), selectedRes.getRating(), selectedRes.getDescription());

        //Uncomment these when add enable field for restaurant
        /*RadioButton enableBtn = (RadioButton) findViewById(R.id.radioEnable);
        if (enableBtn.isChecked()) selectedRes.setEnable("enable");
        else selectedRes.setEnable("disable");*/

        Intent intent = new Intent(this, DisplayManager.class);
        intent.putExtra("cmd", "restaurantSettings");
        startActivity(intent);
    }

    public void discardChange(View v){
        Intent intent = new Intent(this, DisplayManager.class);
        intent.putExtra("cmd", "restaurantSettings");
        startActivity(intent);
    }

    public void backToPrevious(View v){
        discardChange(v);
    }

    private void update(String url, final int resID, final String name, final String rating, final String description) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "Update restaurant successful");
                        } else {
                            Log.d("msg", "Update restaurant fail");
                            Log.d("msg", response);
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
                params.put("idRes", String.valueOf(resID));
                params.put("nameRestaurant", name);
                params.put("ratingRestaurant", rating);
                params.put("descriptionRestaurant", description);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}