package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ip extends AppCompatActivity {
    EditText e1;
    Button btn;
    SharedPreferences sh;
    String ip,lid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=findViewById(R.id.editTextTextPersonName13);
        btn=findViewById(R.id.button9);

        e1.setText(sh.getString("ip",""));

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View view) {
                ip = e1.getText().toString();

                if (ip.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your ip address");
                    e1.requestFocus();
                }
                else {

                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ip);
                    ed.commit();
                    Intent in = new Intent(getApplicationContext(), login.class);
                    startActivity(in);
                }
            }
        });

    }
}