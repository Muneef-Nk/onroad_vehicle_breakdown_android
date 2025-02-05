package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_mechanic extends AppCompatActivity {
    ListView list;
    SharedPreferences sh;
    ArrayList<String> name,place,phone,email;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mechanic);

        list = findViewById(R.id.list);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_mechanic";


        RequestQueue queue = Volley.newRequestQueue(View_mechanic.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name= new ArrayList<>();
                    place=new ArrayList<>();
                    email=new ArrayList<>();
                    phone=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        email.add(jo.getString("email"));
                        phone.add(jo.getString("contact"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

//                    list.setAdapter(new custom3(View_mechanic.this,name,place,phone,email));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_mechanic.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };
        queue.add(stringRequest);

    }

}