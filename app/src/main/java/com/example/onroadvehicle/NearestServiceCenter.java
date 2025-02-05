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

public class NearestServiceCenter extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences sh;

    ListView lst;

    ArrayList<String> sid,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_service_center);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lst = findViewById(R.id.lst_nrst_mech);

        String url ="http://"+sh.getString("ip", "") + ":5000/view_nearest_service";
        RequestQueue queue = Volley.newRequestQueue(NearestServiceCenter.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name= new ArrayList<>();
                    sid=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("name"));
                        sid.add(jo.getString("petrol_id"));


                    }

                     ArrayAdapter<String> ad=new ArrayAdapter<>(NearestServiceCenter.this,android.R.layout.simple_list_item_1,name);
                    lst.setAdapter(ad);

                    lst.setOnItemClickListener(NearestServiceCenter.this);

//                    list.setAdapter(new custom4(NearestServiceCenter.this,name,place,phone,email));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NearestServiceCenter.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        Intent ik = new Intent(getApplicationContext(),Nearest_mechanic.class);
        ik.putExtra("sid",sid.get(i));
        startActivity(ik);
    }
}