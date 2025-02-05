package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class forget extends AppCompatActivity {

    Button btn;
    EditText e1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextEmailAddress);
        btn=findViewById(R.id.button14);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = e1.getText().toString();

               if(email.equalsIgnoreCase(""))
               {
                   e1.setError("Required");
                   e1.requestFocus();
               }
               else
               {
                   RequestQueue queue = Volley.newRequestQueue(forget.this);
                   String url = "http://" + sh.getString("ip","") + ":5000/forgotpassword1";

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

                                   Toast.makeText(forget.this, "Success, check your email", Toast.LENGTH_SHORT).show();

                                   Intent ik = new Intent(getApplicationContext(), login.class);
                                   startActivity(ik);

                               } else {

                                   Toast.makeText(forget.this, "Invalid", Toast.LENGTH_SHORT).show();

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

                   })


                   {
                       @Override
                       protected Map<String, String> getParams() {
                           Map<String, String> params = new HashMap<String, String>();
                           params.put("email", email);

                           return params;
                       }
                   };


                   int MY_SOCKET_TIMEOUT_MS = 100000;

                   stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                           MY_SOCKET_TIMEOUT_MS,
                           DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                           DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                   queue.add(stringRequest);

//                   queue.add(stringRequest);


               }

            }
        });

    }
}