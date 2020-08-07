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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demoapp.Data.Customer;
import com.example.demoapp.Data.Food;

import java.util.HashMap;
import java.util.Map;

public class DisplayFoodSetting extends AppCompatActivity {
    private Food selectedFood;
    private Scene foodSettingScene;
    String urlUpdateData = "http://172.17.23.72:8080/androidwebservice/food/updateMan.php";

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

        foodSettingScene = Scene.getSceneForLayout(root, R.layout.food_setting, this);
        displayFoodSettings(getIntent().getStringExtra("foodName"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayFoodSettings(String foodName){
        Transition slide = new Slide(Gravity.RIGHT);
        TransitionManager.go(foodSettingScene, slide);

        selectedFood = null;
        for(int i = 0; i < MainActivity.foodList.size(); i++){
            selectedFood = MainActivity.foodList.get(i);
            if(foodName.equals(selectedFood.getName())) break;
        }

        //Set value for food setting scene
        ImageView logoView = (ImageView) findViewById(R.id.foodsettings_foodLogo);
        logoView.setImageResource(getMipmapResIdByName(selectedFood.getLogo()));
        EditText nameEdt = (EditText) findViewById(R.id.edt_foodsettings_name);
        nameEdt.setText(selectedFood.getName());
        EditText priceEdt = (EditText) findViewById(R.id.edt_foodsettings_price);
        priceEdt.setText(selectedFood.getPrice());
        EditText quantityEdt = (EditText) findViewById(R.id.edt_foodsettings_quantity);
        quantityEdt.setText(Integer.toString(selectedFood.getQuantity()));
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
        EditText nameEdt = (EditText) findViewById(R.id.edt_foodsettings_name);
        selectedFood.setName(nameEdt.getText().toString());
        EditText priceEdt = (EditText) findViewById(R.id.edt_foodsettings_price);
        selectedFood.setPrice(priceEdt.getText().toString());
        EditText quantityEdt = (EditText) findViewById(R.id.edt_foodsettings_quantity);
        selectedFood.setQuantity(Integer.valueOf(quantityEdt.getText().toString()));

        //Update database
        update(urlUpdateData, selectedFood.getID(), nameEdt.getText().toString(), priceEdt.getText().toString(), quantityEdt.getText().toString());

        Intent intent = null;
        if(DisplayLogin.currentUser.getRole().equals("cook")){
            intent = new Intent(this, DisplayCook.class);
        }
        else{
            intent = new Intent(this, DisplayVendor.class);
        }
        intent.putExtra("cmd", "foodsetting");
        startActivity(intent);


    }

    public void discardChange(View v){
        Intent intent = new Intent(this, DisplayCook.class);
        intent.putExtra("cmd", "foodsetting");
        startActivity(intent);
    }

    public void backToPrevious(View v){
        discardChange(v);
    }

    private void update(String url, final int foodID, final String nameChange, final String priceChange, final String qualityChange) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            Log.d("msg", "Update food successful");
                        } else {
                            Log.d("msg", "Update food fail");
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
                params.put("idFood",String.valueOf(foodID));
                params.put("nameFood", nameChange);
                params.put("quantityFood", qualityChange);
                params.put("priceFood", priceChange);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}