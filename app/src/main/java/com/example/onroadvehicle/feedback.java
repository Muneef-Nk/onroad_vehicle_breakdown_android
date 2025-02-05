package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    EditText t1;
    Button btn;
    SharedPreferences sh;
    RatingBar r1;

    String miid, url1,rate;
    ArrayList<String>name,mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        s1 = findViewById(R.id.spinner);
        t1 = findViewById(R.id.editTextTextPersonName5);
        btn = findViewById(R.id.button8);
        r1 = findViewById(R.id.ratingBar);
       s1.setOnItemSelectedListener(this);


        url1 ="http://"+sh.getString("ip", "") + ":5000/viewmechanic";


        RequestQueue queue = Volley.newRequestQueue(feedback.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    mid= new ArrayList<>();
                    name= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        mid.add(jo.getString("mechanic_id"));
                        name.add(jo.getString("first_name")+" "+jo.getString("last_name"));



                    }

                     ArrayAdapter<String> ad=new ArrayAdapter<>(feedback.this,android.R.layout.simple_list_item_1,name);
                    s1.setAdapter(ad);

//                    li.setAdapter(new Custom(View_result_sarvice_center.this,name,place));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(feedback.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String feed  =t1.getText().toString();
                rate=String.valueOf(r1.getRating());

                if (feed.equalsIgnoreCase(""))
                {
                    t1.setError("please enter your feedback");
                    t1.requestFocus();
                }

                else {
                    RequestQueue queue = Volley.newRequestQueue(feedback.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/send_feedback";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {

                                    Toast.makeText(feedback.this, "send succesfully", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), UserHome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(feedback.this, "Error", Toast.LENGTH_SHORT).show();

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
                            params.put("m_id", miid);
                            params.put("feedback", feed);
                            params.put("rating", rate);
                            params.put("l_id", sh.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        miid=mid.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



