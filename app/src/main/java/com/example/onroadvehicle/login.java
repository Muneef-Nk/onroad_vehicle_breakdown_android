package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2,b3;
    String uname , pswd,url;
    SharedPreferences sh;

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPersonName2);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button11);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1 = findViewById(R.id.textView43);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),forget.class);
                startActivity(i);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=e1.getText().toString();
                pswd=e2.getText().toString();

                if(uname.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your username");
                    e1.requestFocus();
                }
                else if(pswd.equalsIgnoreCase(""))
                {
                    e2.setError("Please enter your password");
                    e2.requestFocus();
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {
                                    String lid = json.getString("id");
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("lid", lid);
                                    ed.apply();

                                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                                    startService(i);

                                    Intent ik = new Intent(getApplicationContext(), UserHome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uname", uname);
                            params.put("pass", pswd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }





            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), Register.class);
                startActivity(ik);

            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LocationService.class);
                startService(i);

                Intent ik = new Intent(getApplicationContext(), public_home.class);
                startActivity(ik);
            }
        });




    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),ip.class);
        startActivity(i);
    }


}