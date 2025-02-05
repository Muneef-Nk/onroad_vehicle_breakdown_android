package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class service_center extends AppCompatActivity implements AdapterView.OnItemClickListener {


    SharedPreferences sh;
   ListView list;
    ArrayList<String> name,place,phone,email,s_id;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        list = findViewById(R.id.list);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_service_center";


        RequestQueue queue = Volley.newRequestQueue(service_center.this);

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
                    s_id=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        email.add(jo.getString("email"));
                        phone.add(jo.getString("phone"));
                        s_id.add(jo.getString("petrol_id"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    list.setAdapter(new custom4(service_center.this,name,place,phone,email));
                    list.setOnItemClickListener(service_center.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(service_center.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lati",LocationService.lati);
                params.put("longi",LocationService.logi);

                return params;
            }
        };
        queue.add(stringRequest);




    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent ik = new Intent(getApplicationContext(),PublicViewMechanic.class);
        ik.putExtra("sid",s_id.get(i));
        startActivity(ik);
    }
}