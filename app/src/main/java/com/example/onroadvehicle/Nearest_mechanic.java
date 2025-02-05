package com.example.onroadvehicle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nearest_mechanic extends AppCompatActivity implements AdapterView.OnItemClickListener {



    ListView list;
    SharedPreferences sh;
    ArrayList<String> name,place,email,phone,mech_id,rating;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_mechanic);



        list = findViewById(R.id.list);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_nearest_mechanic";

        RequestQueue queue = Volley.newRequestQueue(Nearest_mechanic.this);

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
                    mech_id=new ArrayList<>();
                    rating=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        name.add(jo.getString("first_name")+" "+jo.getString("last_name"));
                        place.add(jo.getString("place"));
                        email.add(jo.getString("email"));
                        phone.add(jo.getString("contact"));
                        mech_id.add(jo.getString("login_id"));
                        rating.add(jo.getString("rating"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    list.setAdapter(new custom5(Nearest_mechanic.this,name,place,phone,email,rating));

                    list.setOnItemClickListener(Nearest_mechanic.this);

//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Nearest_mechanic.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("sid",getIntent().getStringExtra("sid"));

                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String mid = mech_id.get(i);

        AlertDialog.Builder ald=new AlertDialog.Builder(Nearest_mechanic.this);
        ald.setTitle("Request")
                .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {



                            RequestQueue queue = Volley.newRequestQueue(Nearest_mechanic.this);
                            url = "http://" + sh.getString("ip","") + ":5000/send_req_location";

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

                                            Toast.makeText(Nearest_mechanic.this, "Success", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), UserHome.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(Nearest_mechanic.this, "Error", Toast.LENGTH_SHORT).show();

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
                                    params.put("uid", sh.getString("lid",""));
                                    params.put("mid", mid);
                                    params.put("lati",LocationService.lati);
                                    params.put("longi",LocationService.logi);

                                    return params;
                                }
                            };
                            queue.add(stringRequest);



                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al=ald.create();
        al.show();
    }
}
