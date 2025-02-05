package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {

    Button b1,b2,b3,b4,b5,b6,b7;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        b2=findViewById(R.id.button5);
        b3=findViewById(R.id.button6);
        b4=findViewById(R.id.button7);

        b5=findViewById(R.id.button4);
//        b6=findViewById(R.id.button15);
        b7=findViewById(R.id.button16);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pump=new Intent(getApplicationContext(),View_result_sarvice_center.class);
                startActivity(pump);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedback=new Intent(getApplicationContext(),feedback.class);
                startActivity(feedback);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout=new Intent(getApplicationContext(),login.class);
                startActivity(logout);
            }
        });


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),NearestServiceCenter.class);
                startActivity(in);
            }
        });
//
//        b6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in=new Intent(getApplicationContext(),Send_request.class);
//                startActivity(in);
//            }
//        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),check_status.class);
                startActivity(in);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
}