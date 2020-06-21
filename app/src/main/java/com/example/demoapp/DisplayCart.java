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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayCart extends AppCompatActivity {

    public static BillManager billManager = new BillManager();

    private Scene cartScene;
    private Scene payMethodScene;

    private static String payment = "Cash";

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
        cartScene = Scene.getSceneForLayout(root, R.layout.cart, this);
        payMethodScene = Scene.getSceneForLayout(root, R.layout.payment,this);

        displayCart();
}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayCart() {
        Transition explode = new Explode();
        TransitionManager.go(cartScene, explode);

        ListView listView = (ListView)findViewById(R.id.listCart);
        listView.setAdapter(new CustomListItemAdapter(this, billManager.getBill().itemList));

        TextView totalPriceTxt = (TextView)findViewById(R.id.cart_totalPrice);
        String totalPrice = billManager.getBill().getTotalPrice();
        totalPriceTxt.setText(totalPrice);

        setPayment();
    }

    public void backToPrevious(View view){
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayCart(View view){
        displayCart();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public void displayPaymentMethod(View view){
        Transition explode = new Explode();
        TransitionManager.go(payMethodScene, explode);
    }

    private void setPayment(){
        //Display payment method
        ImageButton method = (ImageButton)findViewById(R.id.cart_payment);
        if (payment == "Cash") method.setBackgroundResource(R.drawable.cash_payment);
        else if (payment == "MoMo") method.setBackgroundResource(R.drawable.momo_icon);
        else method.setBackgroundResource(R.drawable.mobile_internet_banking);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  cash(View view){
        payment = "Cash";
        displayCart();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  momo(View view){
        payment = "MoMo";
        displayCart();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void  mobile(View view){
        payment = "Mobile";
        displayCart();
    }

    public void displayBill(View view){
        Intent intent = new Intent(this, DisplayBill.class);
        startActivity(intent);
    }
}
