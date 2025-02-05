package com.example.onroadvehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Register extends AppCompatActivity {
    EditText fnam,lnam,plac,pos,pi,emai,phon,usernam,passwor;
    Button b1;
    RadioButton r1,r2;
    SharedPreferences sh;

    String fname , lname, gender, place,post, pin, email, phone, username,password,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fnam=findViewById(R.id.editTextTextPersonName3);
        lnam=findViewById(R.id.editTextTextPersonName4);
        plac=findViewById(R.id.editTextTextPersonName6);
        pos=findViewById(R.id.editTextTextPersonName7);
        pi=findViewById(R.id.editTextTextPersonName8);
        emai=findViewById(R.id.editTextTextPersonName9);
        phon=findViewById(R.id.editTextTextPersonName10);
        usernam=findViewById(R.id.editTextTextPersonName11);
        passwor=findViewById(R.id.editTextTextPersonName12);
        b1=findViewById(R.id.button3);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=fnam.getText().toString();
                lname=lnam.getText().toString();
                place=plac.getText().toString();
                post=pos.getText().toString();
                pin=pi.getText().toString();
                email=emai.getText().toString();
                phone=phon.getText().toString();
                username=usernam.getText().toString();
                password=passwor.getText().toString();
                if (r1.isChecked()){
                    gender=r1.getText().toString();
                }
                else{
                    gender=r2.getText().toString();
                }



                if (fname.equalsIgnoreCase(""))
                {
                    fnam.setError("Please enter your first name");
                    fnam.requestFocus();
                }
                else if (!fname.matches("^[a-z A-Z]*$"))
                {
                    fnam.setError("Only characters are allowed");
                    fnam.requestFocus();
                }
                else if (lname.equalsIgnoreCase(""))
                {
                    lnam.setError("Please enter your last name");
                    lnam.requestFocus();
                }
                else if (!lname.matches("^[a-z A-Z]*$"))
                {
                    lnam.setError("Only characters are allowed");
                    lnam.requestFocus();
                }
                else if (place.equalsIgnoreCase(""))
                {
                    plac.setError("Please enter your place");
                    plac.requestFocus();
                }
                else if (post.equalsIgnoreCase(""))
                {
                    pos.setError("Please enter your post");
                    pos.requestFocus();
                }
                else if (pin.equalsIgnoreCase(""))
                {
                    pi.setError("Please enter your pin");
                    pi.requestFocus();
                }
                else if (pin.length()!=6)
                {
                    pi.setError("Pin number must be 6 numbers");
                    pi.requestFocus();
                }
                else if (email.equalsIgnoreCase(""))
                {
                    emai.setError("Please enter your email address");
                    emai.requestFocus();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emai.setError("Please enter a valid email address");
                    emai.requestFocus();
                }
                else if (phone.equalsIgnoreCase(""))
                {
                    phon.setError("Please enter your phone number");
                    phon.requestFocus();
                }
                else if (phone.length()!=10)
                {
                    phon.setError("Phone number must be 10 numbers");
                    phon.requestFocus();
                }
                else if (username.equalsIgnoreCase(""))
                {
                    usernam.setError("Please enter your username");
                    usernam.requestFocus();
                }
                else if (password.equalsIgnoreCase(""))
                {
                    passwor.setError("Please enter your password");
                    passwor.requestFocus();
                }
                else if (password.length()<8)
                {
                    passwor.setError("Password must be 8 characters long");
                    passwor.requestFocus();
                }

                else {


                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/register";

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
                                    Toast.makeText(Register.this, "registation successfull", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Register.this, "username already exist ", Toast.LENGTH_SHORT).show();

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
                            params.put("Fname", fname);
                            params.put("Lname", lname);
                            params.put("gender", gender);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("email", email);
                            params.put("phone", phone);
                            params.put("username", username);
                            params.put("password", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }



            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }
}