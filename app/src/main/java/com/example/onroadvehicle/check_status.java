package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class check_status extends AppCompatActivity {

    SharedPreferences sh;
    ListView lst;

    ArrayList<String> service_center,mechanic,date,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lst = findViewById(R.id.lst_chk_stts);


        String url ="http://"+sh.getString("ip", "") + ":5000/check_status";


        RequestQueue queue = Volley.newRequestQueue(check_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    service_center= new ArrayList<>();
                    mechanic=new ArrayList<>();
                    date=new ArrayList<>();
                    status=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        service_center.add(jo.getString("name"));
                        mechanic.add(jo.getString("first_name")+ " " +jo.getString("last_name"));
                        date.add(jo.getString("date"));
                        status.add(jo.getString("status"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom4(check_status.this,service_center,mechanic,date,status));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(check_status.this, "err"+error, Toast.LENGTH_SHORT).show();
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


    }
}