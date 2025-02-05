package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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



public class View_result_sarvice_center extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView li;
    SharedPreferences sh;
ArrayList<String> rid,name,place,phone,email,latitude,longitude;
String url;
Spinner s1;

Button b1;
String type[]={"pump","service center"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_sarvice_center);
        li=findViewById(R.id.list2);
        b1=findViewById(R.id.button10);
        s1=findViewById(R.id.spinner2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         ArrayAdapter<String> ad=new ArrayAdapter<>(View_result_sarvice_center.this,android.R.layout.simple_list_item_1,type);
        s1.setAdapter(ad);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url ="http://"+sh.getString("ip", "") + ":5000/view_service_pump";

                RequestQueue queue = Volley.newRequestQueue(View_result_sarvice_center.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            rid= new ArrayList<>();
                            name= new ArrayList<>();
                            place= new ArrayList<>();
                            phone=new ArrayList<>();
                            email=new ArrayList<>();
                            latitude=new ArrayList<>();
                            longitude=new ArrayList<>();

                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                rid.add(jo.getString("petrol_id"));
                                name.add(jo.getString("name"));
                                place.add(jo.getString("place"));
                                phone.add(jo.getString("phone"));
                                email.add(jo.getString("email"));
                                latitude.add(jo.getString("latitude"));
                                longitude.add(jo.getString("longitude"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                    li.setAdapter(new custom4(View_result_sarvice_center.this,name,place,phone,email));
                    li.setOnItemClickListener(View_result_sarvice_center.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener()  {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(View_result_sarvice_center.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("type",s1.getSelectedItem().toString());

                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?q="+latitude.get(i)+","+longitude.get(i)));
        startActivity(intent);
    }
}