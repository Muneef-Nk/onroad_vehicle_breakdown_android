package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class public_home extends AppCompatActivity {

    Button b1,b2,b3;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_home);
//        b1=findViewById(R.id.button14);
        b2=findViewById(R.id.button12);
        b3=findViewById(R.id.button13);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent ik = new Intent(getApplicationContext(), Nearest_mechanic.class);
//                startActivity(ik);
//            }
//        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), service_center.class);
                startActivity(in);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), petrol_pump.class);
                startActivity(ik);
            }
        });


    }
}